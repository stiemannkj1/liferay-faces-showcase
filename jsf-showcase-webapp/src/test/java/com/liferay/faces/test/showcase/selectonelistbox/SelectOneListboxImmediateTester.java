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
public class SelectOneListboxImmediateTester extends SelectTester {

	@Test
	public void runSelectOneListboxImmediateTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(TEST_CONTEXT_URL + "/selectonelistbox/immediate");

		// Wait to begin the test until the submit button is rendered.
		browser.waitForElementVisible(submitButton1Xpath);

		// Test that the first value in the listbox has not yet been clicked by checking
		// the value element is not present in the model value.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the first value in the listbox submits successfully.
		browser.click(option1Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);

		String answer1 = "1";
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);

		// Click the same listbox option to deselect:
		// https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/1899#issuecomment-191480860
		browser.click(option1Xpath);

		// Test that only the third value in the listbox submits successfully.
		browser.click(option3Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer1);

		String answer3 = "3";
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer1);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer3);
		SeleniumAssert.assertElementVisible(browser, immediateMessage1Xpath);

		// Test that the first value in the second listbox submits successfully.
		browser.click("((//div[@class='showcase-example-usage'])[2]/select/option)[1]");
		browser.clickAndWaitForAjaxRerender(submitButton2Xpath);
		SeleniumAssert.assertElementTextVisible(browser, "(//div[@class='results-content'])[2]", answer1);

		// Click the same listbox option to deselect:
		// https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/1899#issuecomment-191480860
		browser.click("((//div[@class='showcase-example-usage'])[2]/select/option)[1]");

		// Test that only the third value in the second listbox submits successfully.
		browser.click("((//div[@class='showcase-example-usage'])[2]/select/option)[3]");
		browser.clickAndWaitForAjaxRerender(submitButton2Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, "(//div[@class='results-content'])[2]", answer1);
		SeleniumAssert.assertElementTextVisible(browser, "(//div[@class='results-content'])[2]", answer3);
		SeleniumAssert.assertElementVisible(browser, immediateMessage2Xpath);
	}
}
