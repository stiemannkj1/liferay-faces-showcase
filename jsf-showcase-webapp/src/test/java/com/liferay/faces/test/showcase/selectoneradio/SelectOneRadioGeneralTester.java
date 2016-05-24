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
package com.liferay.faces.test.showcase.selectoneradio;

import java.util.List;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectOneRadioGeneralTester extends SelectOneRadioTester {

	@Test
	public void runSelectOneRadioGeneralTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(selectOneRadioURL + "/general");

		// Wait to begin the test until the submit button is rendered.
		browser.waitForElementVisible(submitButton1Xpath);

		List<WebElement> renderedCheckboxElements = browser.findElements(By.xpath(renderedCheckboxXpath));

		if (renderedCheckboxElements.size() == 0) {
			// TODO
			// log "No renderedCheckbox ..."
		}
		else {
			// TODO
			// Do we want to exercise the renderedCheckbox ?
		}

		// Test that the web page shows an error message when a value is required and an empty value is submitted.
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementNotPresent(browser, error1Xpath);
		browser.click(requiredCheckboxXpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementVisible(browser, error1Xpath);

		// Test that the first value of the radio has not yet been submitted by checking
		// the value element is not present in the model value.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the first value in the menu submits successfully.
		browser.click(
			"(//div[@class='showcase-example-usage']/div/table/tbody/tr/td)[1]/input[contains(@id,':selectOneRadio')]");

		String answer1 = "1";
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);

		// Test that only the fourth value in the menu submits successfully.
		browser.click(
			"(//div[@class='showcase-example-usage']/div/table/tbody/tr/td)[4]/input[contains(@id,':selectOneRadio')]");
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer1);

		String answer4 = "4";
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer4);
	}
}
