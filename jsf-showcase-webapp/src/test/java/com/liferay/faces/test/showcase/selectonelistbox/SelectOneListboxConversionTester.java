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
package com.liferay.faces.test.showcase.selectonelistbox;

import org.junit.Test;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.select.SelectTester;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectOneListboxConversionTester extends SelectTester {

	@Test
	public void runSelectOneListboxConversionTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(TEST_CONTEXT_URL + "/selectonelistbox/conversion");

		// Wait to begin the test until an element is rendered.
		browser.waitForElementVisible(modelValue1Xpath);

		// Test that the first value in the listbox has not yet been clicked by checking
		// the value element is not present in the model value.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the first incorrect value in the listbox submits successfully.
		browser.click(option1Xpath);

		String answer1 = "Oct 31, 1517 AD";
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);
		SeleniumAssert.assertElementVisible(browser, conversionIncorrectMessage1Xpath);

		// Click the same listbox option to deselect:
		// https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/1899#issuecomment-191480860
		browser.click(option1Xpath);

		// Test that only the third correct value in the listbox submits successfully.
		browser.click(option2Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer1);

		String answer2 = "Jul 4, 1776 AD";
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer2);
		SeleniumAssert.assertElementVisible(browser, conversionCorrectMessage2Xpath);
	}
}
