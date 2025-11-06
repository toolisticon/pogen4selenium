package io.toolisticon.pogen4selenium.processor.pageobject;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.cute.matchers.CoreGeneratedFileObjectMatchers;
import io.toolisticon.pogen4selenium.processor.datatoextract.DataObjectProcessor;
import io.toolisticon.pogen4selenium.processor.pageobject.PageObjectProcessor;


/**
 * Tests of {@link io.toolisticon.pogen4selenium.api.PageObject}.
 *
 * TODO: replace the example testcases with your own testcases
 */

public class PageObjectProcessorTest {


    CuteApi.BlackBoxTestSourceFilesInterface compileTestBuilder;

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);

        compileTestBuilder = Cute
                .blackBoxTest()
                .given()
                .processors(PageObjectProcessor.class);
    }


    @Test
    public void test_valid_usage() {

        compileTestBuilder
                .andSourceFiles("testcases/pageobject/TestcaseValidUsage.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                //.compilationFails()
                .executeTest();
    }
    
    @Test
    public void test_invalid_interfaceWithTypeParameter() {

        compileTestBuilder
                .andSourceFiles("testcases/pageobject/TestcaseInterfaceWithTypeVars.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationFails()
                .andThat().compilerMessage().ofKindError().contains(PageObjectProcessorCompilerMessages.ERROR_COULD_INTERFACE_MUST_NOT_HAVE_TYPEPARAMETERS.getCode())
                .executeTest();
    }
    
    @Test
    public void test_valid_withUrlCheck() {

        compileTestBuilder
                .andSourceFiles("testcases/pageobject/TestcaseValidUsageWithUrlCheck.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();
    }
    
    
    @Test
    public void test_valid_withInterPackageReferences() {

        compileTestBuilder
                .andSourceFiles("testcases/pageobject/referenceBetweenPackages/ReferencingClass.java","testcases/pageobject/referenceBetweenPackages/ReferencedClass.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();
    }
    
    
    @Test
    public void test_valid_withLocatorInActions() {

        compileTestBuilder
                .andSourceFiles("testcases/pageobject/locatorInActions/TestcaseValidUsage.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();
    }
    
    @Test
    public void test_valid_withSynchronizedAction() {

        compileTestBuilder
                .andSourceFiles("testcases/pageobject/synchronized/TestcaseValidUsage.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .andThat().generatedSourceFile("io.toolisticon.pogen4selenium.processor.tests.LoginPageImpl")
                .matches(CoreGeneratedFileObjectMatchers.createContainsSubstringsMatcher("synchronized"))
                .executeTest();
    }
    
    @Test
    public void test_valid_example() {

    	Cute
        .blackBoxTest()
        .given()
        .processors(PageObjectProcessor.class, DataObjectProcessor.class)
                .andSourceFiles("testcases/pageobject/example/TestPagePageObject.java", "testcases/pageobject/example/TestPageTableEntry.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                .executeTest();
    }
    
    /*-
    @Test
    public void test_readAnnotatedValue() {
    	Cute.unitTest()
    	.when()
    	.passInElement().fromSourceFile("testcases/pageobject/TestcaseValidUsage.java")
    	.intoUnitTest((procEnv,element) ->{
    		
    		try {
    			ToolingProvider.setTooling(procEnv);
	    		PageObjectElementOnConstantWrapper wrapper = PageObjectElementOnConstantWrapper.wrap(element);
	    		String value = wrapper.value();
	    		MatcherAssert.assertThat(value, Matchers.is("username"));
    		} finally {
    			ToolingProvider.clearTooling();
    		}
    		
    	}).executeTest();
    	
    }
    */
    


   

}