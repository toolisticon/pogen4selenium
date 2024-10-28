# pogen4selenium - a page object generator 4 selenium

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.pogen4selenium/pogen4selenium/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.pogen4selenium/pogen4selenium)


# Why you should use this project?

Page Objects are a good way to produce a maintainable and reusable codebase to implement automated ui tests with selenium.
Nevertheless those page objects can contain a lot of (selenium related) boilerplate code that is mostly reused via copy and paste.
This project provides processors to generate page object implementations by processing annotations placed on interfaces. 
By doing that it drastically increases readability of page objects and reduces time for development.



# Features

- generates page object class implementations based on annotated interfaces
- page objects support inheritance - interfaces can extend multiple other interfaces and will inherit all of their elements and methods
- generates extraction data class implementations base on annotated interfaces.
- actions like clicking of elements or writing to input fields can be configured via annotations
- the api enforces creation of a fluent api that improves writing of tests. Doing assertions or executing of custom code is also embedded into this fluent api

# Restrictions

The project is still in development, so it currently just supports a few Actions like clicking or writing to input fields. Other Actions will be added in the near future.

Please create an issue, if you need specific action to be implemented. Usually it will be included shortly after ;)

# How does it work?

## Project Setup

The api lib must be bound as a dependency - for example in maven:
```xml
<dependencies>

	<dependency>
	    <groupId>io.toolisticon.pogen4selenium</groupId>
	    <artifactId>pogen4selenium-api</artifactId>
	    <version>0.1.0</version>
	    <scope>provided</scope>
	</dependency>
 
</dependencies>
```

Additionally, you need to declare the annotation processor path in your compiler plugin:

```xml
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>

    <configuration>
        
        <annotationProcessorPaths>
            <path>
                <groupId>io.toolisticon.pogen4selenium</groupId>
                <artifactId>pogen4selenium-processor</artifactId>
                <version>0.1.0</version>
            </path>
        </annotationProcessorPaths>
        
    </configuration>

</plugin>
```

## Documentation

### Defining Page Objects
The page object interfaces must be annotated with the *PageObject* annotation and must extend the *PageObjectParent* interface.

It's possible to add String constants annotated with the *PageObjectElement* annotation for all web elements which are related with the page object. The constant values must be unique inside the interface and will be used in the generated class as variable names for the corresponding elements. The constant names must have "_ID" as suffix.

Then it's possible to add methods to the interface to declare actions. Those methods must return another page object interface.

It's also possible to add methods to extract data. Those methods must be annotated with the *ExtractData* annotation and must return an instance or List of a data extraction type (see next section)

You are able to use default methods to use custom code.

Our [example website](pogen4selenium-example/src/test/resources/TestPage.html) is just a simple html file. This example demonstrates how to setup the page object:


```java
@PageObject
public interface TestPagePageObject extends PageObjectParent<TestPagePageObject>{

	static final String DATA_EXTRACTION_FROM_TABLE_XPATH = "//table//tr[contains(@class,'data')]";
	
	@PageObjectElement(
		elementVariableName = TestPagePageObject.INPUT_FIELD_ID, 
		by = _By.ID,
		value=""
	)
	static final String INPUT_FIELD_ID = "searchField";
	
	@PageObjectElement(
		elementVariableName = TestPagePageObject.COUNTER_INCREMENT_BUTTON_ID,  		
		value = "//fieldset[@name='counter']/input[@type='button']" 
	)
	static final String COUNTER_INCREMENT_BUTTON_ID = "counterIncrementButton";
	
	@ActionMoveToAndClick(COUNTER_INCREMENT_BUTTON_ID)
	@Pause(value = 500L)
	TestPagePageObject clickCounterIncrementButton();
	
	
	@ExtractData(value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	List<TestPageTableEntry> getTableEntries();
	
	@ExtractData(value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	TestPageTableEntry getFirstTableEntry();
	
	@ExtractDataValue(value = "//fieldset[@name='counter']/span[@id='counter']")
	String getCounter();
	
	// you can always provide your own methods and logic
	default String providedGetCounter() {
		return getDriver().findElement(By.xpath("//fieldset[@name='counter']/span[@id='counter']")).getText();
	}
	
	// Custom entry point for starting your tests
	public static TestPagePageObject init(WebDriver driver) {
		driver.get("http://localhost:9090/start");
		return new TestPagePageObjectImpl(driver);
	}

}
```


### Defining Interfaces For Data Extraction

Interfaces for data extraction must be annotated with the *DataToExtract* annotation.
The methods for reading extracted data values must be  annotated with the *DataToExtractValue* annotation. Like with selenium it's possible to use different mechanics to locate the web elements to read the values from. Please make sure to use to prefix xpath expressions with "./" to locate elements relative to the Root element for extraction defined in the page objects extraction method.

Extracting table data for our small [example](pogen4selenium-example/src/test/resources/TestPage.html):

```java
@DataObject
public interface TestPageTableEntry {

	// will use XPATH locator by default if by attribute isn't set explicitly
	@ExtractDataValue(by = _By.XPATH, value = "./td[1]")
	String name();
	
	@ExtractDataValue( value = "./td[2]")
	String age();
	
	@ExtractDataValue( value = "./td[3]/a",  kind = Kind.ATTRIBUTE, name = "href")
	String link();
	
	@ExtractDataValue(, value = "./td[3]/a", kind = Kind.TEXT)
	String linkText();
	
}
```


### Writing tests
Writing tests is easy. The fluent api provides a *doAssertions* method that allows you to inline custom assertions done with your favorite unit testing tool.

```java
public class TestPageTest {


	private WebDriver webDriver;
	private JettyServer jettyServer;
	
	@Before
	public void init() throws Exception{
		
		jettyServer = new JettyServer();
		jettyServer.start();
		
		webDriver = new EdgeDriver();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@After
	public void cleanup() throws Exception{
		webDriver.quit();
		jettyServer.stop();
	}
	
	@Test
	public void extractDatasetsTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				
			// Do assertions here
			List<TestPageTableEntry> results = e.getTableEntries();
			
			MatcherAssert.assertThat(results, Matchers.hasSize(2));
			
			MatcherAssert.assertThat(results.getFirst().name(), Matchers.is("Max"));
			MatcherAssert.assertThat(results.getFirst().age(), Matchers.is("9"));
			MatcherAssert.assertThat(results.getFirst().link(), Matchers.is("https://de.wikipedia.org/wiki/Max_und_Moritz"));
			MatcherAssert.assertThat(results.getFirst().linkText(), Matchers.is("Max und Moritz Wikipedia"));
			
			
			MatcherAssert.assertThat(results.get(1).name(), Matchers.is("Moritz"));
			MatcherAssert.assertThat(results.get(1).age(), Matchers.is("10"));
			MatcherAssert.assertThat(results.get(1).link(), Matchers.is("https://de.wikipedia.org/wiki/Wilhelm_Busch"));
			MatcherAssert.assertThat(results.get(1).linkText(), Matchers.is("Wilhelm Busch Wikipedia"));
				

			});
	}
	
	@Test
	public void extractFirstDatasetTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				
			// Do assertions here
			TestPageTableEntry result = e.getFirstTableEntry();
			
			
			MatcherAssert.assertThat(result.name(), Matchers.is("Max"));
			MatcherAssert.assertThat(result.age(), Matchers.is("9"));
			MatcherAssert.assertThat(result.link(), Matchers.is("https://de.wikipedia.org/wiki/Max_und_Moritz"));
			MatcherAssert.assertThat(result.linkText(), Matchers.is("Max und Moritz Wikipedia"));
			
	

		});
	}
	
	@Test
	public void incrementCounterTest() {
		TestPagePageObject.init(webDriver)
			.doAssertions(e -> {
					MatcherAssert.assertThat(e.getCounter(), Matchers.is("1"));
				})
			.clickCounterIncrementButton()
			.doAssertions(e -> {
				MatcherAssert.assertThat(e.getCounter(), Matchers.is("2"));
			})
			.clickCounterIncrementButton()
			.clickCounterIncrementButton()
			.doAssertions(e -> {
				MatcherAssert.assertThat(e.getCounter(), Matchers.is("4"));
			});
	}
	
	
}

```

#### Methods provided by fluent api

There are some default methods provided by the fluent api:

##### verify
By using the verify methods it's possible to do check state of elements, i.e. if url matches a regular expression or if elements are present or clickable. Expected state is configured in PageObjectElement annotation. If not set explicitly all elements are expected to be present by default.

##### doAssertions
It's possible to inline assertions done via your favorite testing tools. 
By providing this method it's not necessary to hassle with local variables anymore.

##### execute
The execute method allows you to do test steps dynamically, like reading data from the web page and doing things based on the extracted data.
It can also be used to switch to another page object type. This can be useful if input data is expected to be validated and should stay on the same page and show an error message.

##### pause
It's possible to enforce an explicit pause time by using this method

##### changePageObjectType
Change the page objects type if expected behavior leaves the 'happy path' - for example if you expect to encounter a failing form validation or similar things.
 


## Best practices

There are a few things you should consider as best practices

- Naming convention: Please use specific prefixes for you page object methods. This can be 'do' for all actions and 'get' for reading data.
- Page objects should define just the happy path. Special cases like failing validations can be handled in the unit tests via the execute method(you can map to another page object type in it).   

## Example

Please check the our [example](pogen4selenium-example/)
    
# Contributing

We welcome any kind of suggestions and pull requests.

## Building and developing the pogen4selenium annotation processor

The pogen4selenium is built using Maven.
A simple import of the pom in your IDE should get you up and running. To build the pogen4selenium on the commandline, just run `mvn` or `mvn clean install`

## Requirements

The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions


# License

This project is released under the revised [MIT License](LICENSE).

This project includes and repackages the [Annotation-Processor-Toolkit](https://github.com/holisticon/annotation-processor-toolkit) released under the  [MIT License](/3rdPartyLicenses/annotation-processor-toolkit/LICENSE.txt).
