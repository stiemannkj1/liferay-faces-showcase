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
package com.liferay.faces.test.showcase.selectmanycheckbox;

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
public class SelectManyCheckboxGeneralTester extends SelectManyCheckboxTester {

	@Test
	public void runSelectManyCheckboxGeneralTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(selectmanycheckboxURL + "/general");

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

		// Test that the checked values submit successfully.
		String manyCheckbox1Xpath =
			"(//div[@class='showcase-example-usage']/div/table/tbody/tr/td)[1]/input[contains(@id,':selectManyCheckbox')]";
		String manyCheckbox2Xpath =
			"(//div[@class='showcase-example-usage']/div/table/tbody/tr/td)[2]/input[contains(@id,':selectManyCheckbox')]";
		String manyCheckbox4Xpath =
			"(//div[@class='showcase-example-usage']/div/table/tbody/tr/td)[4]/input[contains(@id,':selectManyCheckbox')]";
		browser.click(manyCheckbox1Xpath);
		browser.click(manyCheckbox2Xpath);
		browser.click(manyCheckbox4Xpath);

		String answer1 = "1";
		String answer2 = "2";
		String answer4 = "4";
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer2);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer4);
	}
}
