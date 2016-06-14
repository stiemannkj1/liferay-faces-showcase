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

import org.junit.Test;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectManyCheckboxConversionTester extends SelectManyCheckboxTester {

	@Test
	public void runSelectManyCheckboxConversionTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(selectmanycheckboxURL + "/conversion");

		// Wait to begin the test until an element is rendered.
		browser.waitForElementVisible(modelValue1Xpath);

		// Test that the first checkbox has not yet been clicked by checking the value element is not present.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the checked values submit successfully.
		browser.click(manyCheckbox1Xpath);
		browser.click(manyCheckbox3Xpath);
		browser.click(manyCheckbox4Xpath);

		String answer1 = "Apr 5, 0033 AD";
		String answer3 = "Jul 4, 1776 AD";
		String answer4 = "Jul 14, 1789 AD";
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer3);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer4);

		// Test that the incorrect and correct checked values submit successfully.
		SeleniumAssert.assertElementVisible(browser, conversionIncorrectMessage1Xpath);
		browser.click(manyCheckbox1Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementVisible(browser, conversionCorrectMessage2Xpath);

	}
}
