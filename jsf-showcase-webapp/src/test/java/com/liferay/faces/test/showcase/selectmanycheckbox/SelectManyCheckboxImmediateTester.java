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
public class SelectManyCheckboxImmediateTester extends SelectManyCheckboxTester {

	@Test
	public void runSelectManyCheckboxImmediateTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(selectmanycheckboxURL + "/immediate");

		// Wait to begin the test until the submit button is rendered.
		browser.waitForElementVisible(submitButton1Xpath);

		// Test that the first checkbox has not yet been clicked by checking the value element is not present.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the checked values submit successfully.
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
		SeleniumAssert.assertElementVisible(browser, immediateMessage1Xpath);

		// Test that the second checked values submit successfully.
		String manyCheckbox1Xpath2 =
			"((//div[@class='showcase-example-usage'])[2]/table/tbody/tr/td)[1]/input[contains(@id,':selectManyCheckbox')]";
		String manyCheckbox2Xpath2 =
			"((//div[@class='showcase-example-usage'])[2]/table/tbody/tr/td)[2]/input[contains(@id,':selectManyCheckbox')]";
		String manyCheckbox4Xpath2 =
			"((//div[@class='showcase-example-usage'])[2]/table/tbody/tr/td)[4]/input[contains(@id,':selectManyCheckbox')]";
		browser.click(manyCheckbox1Xpath2);
		browser.click(manyCheckbox2Xpath2);
		browser.click(manyCheckbox4Xpath2);
		browser.clickAndWaitForAjaxRerender(submitButton2Xpath);

		String modelValue2Xpath = "(//div[@class='results-content'])[2]";
		SeleniumAssert.assertElementTextVisible(browser, modelValue2Xpath, answer1);
		SeleniumAssert.assertElementTextVisible(browser, modelValue2Xpath, answer2);
		SeleniumAssert.assertElementTextVisible(browser, modelValue2Xpath, answer4);
		SeleniumAssert.assertElementVisible(browser, immediateMessage2Xpath);
	}
}
