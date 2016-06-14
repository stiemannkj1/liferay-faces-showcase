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
package com.liferay.faces.test.showcase.selectmanylistbox;

import java.util.List;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.select.SelectTester;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectManyListboxGeneralTester extends SelectTester {

	@Test
	public void runSelectManyListboxGeneralTest() throws Exception {
		Browser browser = Browser.getInstance();
		browser.get(TEST_CONTEXT_URL + "/selectmanylistbox/general");

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
		String select1Xpath = "//select[contains(@id,':selectManyListbox')]";
		Select select = new Select(browser.findElementByXpath(select1Xpath));
		select.deselectAll();
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementNotPresent(browser, error1Xpath);
		browser.click(requiredCheckboxXpath);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementVisible(browser, error1Xpath);
		select = new Select(browser.findElementByXpath(select1Xpath));
		select.deselectAll();

		// Test that the first value in the listbox submits successfully.
		String option1 = "(//div[@class='showcase-example-usage']/div/select/option)[2]";
		browser.click(option1);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);

		String answer1 = "1";
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer1);

		// Click the same listbox option to deselect:
		// https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/1899#issuecomment-191480860
		browser.click(option1);

		// Test that only the third value in the listbox submits successfully.
		String option3 = "(//div[@class='showcase-example-usage']/div/select/option)[4]";
		browser.click(option3);
		browser.clickAndWaitForAjaxRerender(submitButton1Xpath);
		SeleniumAssert.assertElementTextNotVisible(browser, modelValue1Xpath, answer1);

		String answer3 = "3";
		SeleniumAssert.assertElementTextVisible(browser, modelValue1Xpath, answer3);
	}
}
