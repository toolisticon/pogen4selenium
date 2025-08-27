package io.toolisticon.pogen4selenium.runtime;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.RetryCause;

public class NoSuchElementExceptionCause implements RetryCause {

	@Override
	public boolean applies(Exception e, WebDriver driver) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!  do retry check : " + NoSuchElementExceptionCause.class.getName());
		return e != null && NoSuchElementException.class.equals(e.getClass());
	}

}
