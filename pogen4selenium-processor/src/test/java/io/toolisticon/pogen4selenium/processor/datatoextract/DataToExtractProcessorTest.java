package io.toolisticon.pogen4selenium.processor.datatoextract;

import org.junit.Before;
import org.junit.Test;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.Cute;
import io.toolisticon.cute.CuteApi;
import io.toolisticon.pogen4selenium.processor.datatoextract.DataObjectProcessor;


/**
 * Tests of {@link io.toolisticon.pogen4selenium.api.DataObject}.
 *
 */

public class DataToExtractProcessorTest {


    CuteApi.BlackBoxTestSourceFilesInterface compileTestBuilder;

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);

        compileTestBuilder = Cute
                .blackBoxTest()
                .given()
                .processors(DataObjectProcessor.class);
    }


    @Test
    public void test_valid_usage() {

        compileTestBuilder
                .andSourceFiles("testcases/datatoextract/TestcaseValidUsage.java")
                .whenCompiled()
                .thenExpectThat()
                .compilationSucceeds()
                //.compilationFails()
                .executeTest();
    }

}