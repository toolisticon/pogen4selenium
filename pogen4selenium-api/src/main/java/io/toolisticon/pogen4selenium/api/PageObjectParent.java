package io.toolisticon.pogen4selenium.api;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.runtime.AssertionInterface;
import io.toolisticon.pogen4selenium.runtime.ExecuteBlock;

public interface PageObjectParent<PAGEOBJECT extends PageObjectParent<PAGEOBJECT>> {

	PAGEOBJECT verify();

	WebDriver getDriver();

	PAGEOBJECT pause(Duration duration);

	PAGEOBJECT doAssertions(AssertionInterface<PAGEOBJECT> function);

	<OPO extends PageObjectParent<OPO>> OPO execute(ExecuteBlock<PAGEOBJECT, OPO> function);

}
