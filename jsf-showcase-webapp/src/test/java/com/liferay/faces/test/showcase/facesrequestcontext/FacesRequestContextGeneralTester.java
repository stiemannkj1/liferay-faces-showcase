/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.test.showcase.facesrequestcontext;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.buttonlink.ButtonLinkTester;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class FacesRequestContextGeneralTester extends ButtonLinkTester {

	@Test
	public void runFacesRequestContextGeneralTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser,"util", "facesrequestcontext", "general");

		// Test facesrequestcontext modal is visible.
		browser.click("(//*[contains(@value,'Show Modal')])[1]");
		browser.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(submitButton1Xpath)));
		SeleniumAssert.assertElementVisible(browser, submitButton1Xpath);

		// Test that the web page shows an error message when a value of null is submitted.
		browser.click(submitButton1Xpath);
		SeleniumAssert.assertElementVisible(browser, error1Xpath);
		String nullErrorTextXpath = "(//div[contains(@class,'field form-group has-error') or contains(@class,'field control-group error')])/text()='Email: Validation Error: Value is required.'";
		Assert.assertTrue(nullErrorTextXpath.contains("Email: Validation Error: Value is required."));

		// Test that the web page shows an error message when an invalid value is submitted.
		String emailAddressInputTextXpath = "(//input[contains(@id,':text')])[1]";
		String invalidText = "HelloWorldcom";
		browser.sendKeys(emailAddressInputTextXpath, invalidText);
		browser.click(submitButton1Xpath);
		SeleniumAssert.assertElementVisible(browser, error1Xpath);
		String invalidErrorTextXpath = "(//div[contains(@class,'field form-group has-error') or contains(@class,'field control-group error')])/text()='Invalid E-Mail Address'";
		Assert.assertTrue(invalidErrorTextXpath.contains("Invalid E-Mail Address"));

		// Test that a valid value submits successfully.
		WebElement input = browser.findElementByXpath(emailAddressInputTextXpath);
		input.clear();

		String text = "hello@world.com";
		browser.sendKeys(emailAddressInputTextXpath, text);
		browser.click(submitButton1Xpath);
		String modalNotDisplayedXpath = "(//span[contains(@style,'display: none;')])[1]";
		browser.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(modalNotDisplayedXpath)));
		SeleniumAssert.assertElementPresent(browser, modalNotDisplayedXpath);
	}
}
