# pogen4selenium - a page object generator 4 selenium

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.pogen4selenium/pogen4selenium/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.pogen4selenium/pogen4selenium)


# Why you should use this project?

Page Objects are a good way to produce a maintainable and reusable codebase to implement automated ui tests with selenium.
Nevertheless those page objects can contain a lot of (selenium related) boilerplate code that is mostly reused via copy and paste.
This project provides processors to generate page object implementations by processing annotations placed on interfaces. 
By doing that it drastically increases readability of page objects and reduces time for development.



# Features

- generates page object class implementations based on annotated interfaces
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
	
	@PageObjectElement(elementVariableName=TestPagePageObject.INPUT_FIELD_ID, by = By.ID,  value="" )
	static final String INPUT_FIELD_ID = "searchField";
	
	@PageObjectElement(elementVariableName=TestPagePageObject.COUNTER_INCREMENT_BUTTON_ID, by = By.XPATH,  value="//fieldset[@name='counter']/input[@type='button']" )
	static final String COUNTER_INCREMENT_BUTTON_ID = "counterIncrementButton";
	
	@ActionMoveToAndClick(COUNTER_INCREMENT_BUTTON_ID)
	TestPagePageObject clickCounterIncrementButton();
	
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api.By.XPATH, value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	List<TestPageTableEntry> getTableEntries();
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api.By.XPATH, value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	TestPageTableEntry getFirstTableEntry();
	
	default String getCounter() {
		return getDriver().findElement(org.openqa.selenium.By.xpath("//fieldset[@name='counter']/span[@id='counter']")).getText();
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

	@ExtractDataValue(by = By.XPATH, value = "./td[1]")
	String name();
	
	@ExtractDataValue(by = By.XPATH, value = "./td[2]")
	String age();
	
	@ExtractDataValue(by = By.XPATH, value = "./td[3]/a",  kind = Kind.ATTRIBUTE, name = "href")
	String link();
	
	@ExtractDataValue(by = By.XPATH, value = "./td[3]/a", kind = Kind.TEXT)
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
