package io.toolisticon.pogen4selenium.processor.pageobject;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link PageObjectProcessorMessages}.
 *
 * TODO: replace the example testcases with your own testcases
 *
 */
public class PageObjectProcessorMessagesTest {

    @Test
    public void test_enum() {

        MatcherAssert.assertThat(PageObjectProcessorCompilerMessages.ERROR_COULD_NOT_CREATE_CLASS.getCode(), Matchers.startsWith("PageObject"));
        MatcherAssert.assertThat(PageObjectProcessorCompilerMessages.ERROR_COULD_NOT_CREATE_CLASS.getMessage(), Matchers.containsString("create class"));

    }


}
