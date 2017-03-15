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
/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.showcase.select;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.showcase.TesterBase;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class SelectTester extends TesterBase {

	// Common Xpath
	protected static final String conversionCorrectMessage1Xpath = "//li[contains(text(),'Correct!')]";
	protected static final String conversionIncorrectMessage1Xpath = "//li[contains(text(),'Incorrect!')]";
	protected static final String select1Xpath = "(//select[contains(@id, 'select')])[1]";
	protected static final String select2Xpath = "(//select[contains(@id, 'select')])[2]";

	// Protected Constants
	protected static final String OPTION_CHILD_XPATH = "/option";

	protected void clickAndWaitForAjaxRerender(Browser browser, String xpath) {

		// SelectOneMenu and SelectManyMenu tests occasionally fail due to elements moving off screen, so center the
		// element in the view.
		if (this.getClass().getName().contains("Menu")) {
			browser.centerElementInView(xpath);
		}

		browser.clickAndWaitForAjaxRerender(xpath);
	}
}
