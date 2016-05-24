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
package com.liferay.faces.test.showcase.selectonemenu;

import org.junit.Test;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.select.SelectTester;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectOneMenuInstantAjaxTester extends SelectTester {

	@Test
	public void runSelectOneMenuInstantAjaxTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(TEST_CONTEXT_URL + "/selectonemenu/instant-ajax");

		// Wait to begin the test until an element is rendered.
		browser.waitForElementVisible(option4Xpath);

		// Test that the first value in the menu has not yet been clicked by checking
		// the value element is not present in the model value.
		SeleniumAssert.assertElementNotPresent(browser, modelValueElement1Xpath);

		// Test that the third value in the menu submits successfully.
		String select1Xpath = "//select[contains(@id,':selectOneMenu')]";
		String answer3 = "3";
		selectByValueAndWaitForAjaxRerender(browser, select1Xpath, answer3);

		// Test that only the first value in the menu submits successfully.
		String answer1 = "1";
		selectByValueAndWaitForAjaxRerender(browser, select1Xpath, answer1);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer3);
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);
	}
}
