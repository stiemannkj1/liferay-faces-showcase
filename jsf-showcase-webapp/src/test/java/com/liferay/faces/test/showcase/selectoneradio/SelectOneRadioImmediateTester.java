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

import org.junit.Test;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectOneRadioImmediateTester extends SelectOneRadioTester {

	@Test
	public void runSelectOneRadioImmediateTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(selectOneRadioURL + "/immediate");

		// Wait to begin the test until the submit button is rendered.
		browser.waitForElementVisible(submitButton1Xpath);

		// Test that the first value of the radio has not yet been submitted by checking
		// the value element is not present in the model value.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the first value in the menu submits successfully.
		browser.click(oneRadio1Xpath);

		String answer1 = "1";
		browser.centerElementInView(submitButton1Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);

		// Test that only the fourth value in the menu submits successfully.
		browser.click(oneRadio4Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer1);

		String answer4 = "4";
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer4);
		SeleniumAssert.assertElementVisible(browser, immediateMessage1Xpath);

		// Test that the second checked values submit successfully.
		String oneRadio1Xpath2 =
			"((//div[@class='showcase-example-usage'])[2]/table/tbody/tr/td)[1]/input[contains(@id,':selectOneRadio')]";
		browser.click(oneRadio1Xpath2);
		browser.centerElementInView(submitButton2Xpath);
		browser.clickAndWaitForAjaxRerender(submitButton2Xpath);

		String modelValue2Xpath = "(//div[@class='results-content'])[2]";
		SeleniumAssert.assertElementTextVisible(browser, modelValue2Xpath, answer1);

		// Test that only the fourth value in the menu submits successfully.
		String oneRadio4Xpath2 =
			"((//div[@class='showcase-example-usage'])[2]/table/tbody/tr/td)[4]/input[contains(@id,':selectOneRadio')]";
		browser.click(oneRadio4Xpath2);
		browser.clickAndWaitForAjaxRerender(submitButton2Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue2Xpath, answer1);
		SeleniumAssert.assertElementTextVisible(browser, modelValue2Xpath, answer4);
		SeleniumAssert.assertElementVisible(browser, immediateMessage2Xpath);
	}
}
