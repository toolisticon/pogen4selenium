package io.toolisticon.pogen4selenium.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;


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
                .andSourceFiles("testcases/TestcaseValidUsage.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                //.compilationFails()
                .executeTest();
    }
    
    /*-
    @Test
    public void test_readAnnotatedValue() {
    	Cute.unitTest()
    	.when()
    	.passInElement().fromSourceFile("testcases/TestcaseValidUsage.java")
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