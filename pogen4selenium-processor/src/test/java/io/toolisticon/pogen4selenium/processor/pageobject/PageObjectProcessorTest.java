package io.toolisticon.pogen4selenium.processor.pageobject;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
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