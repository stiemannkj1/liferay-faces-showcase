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

import com.liferay.faces.test.showcase.select.SelectTester;


/**
 * @author  Kyle Stiemann
 */
public class SelectManyCheckboxTester extends SelectTester {

	protected static final String selectmanycheckboxURL = TEST_CONTEXT_URL + "/selectmanycheckbox";
	protected static final String manyCheckbox1Xpath =
		"(//div[@class='showcase-example-usage']/table/tbody/tr/td)[1]/input[contains(@id,':selectManyCheckbox')]";
	protected static final String manyCheckbox2Xpath =
		"(//div[@class='showcase-example-usage']/table/tbody/tr/td)[2]/input[contains(@id,':selectManyCheckbox')]";
	protected static final String manyCheckbox3Xpath =
		"(//div[@class='showcase-example-usage']/table/tbody/tr/td)[3]/input[contains(@id,':selectManyCheckbox')]";
	protected static final String manyCheckbox4Xpath =
		"(//div[@class='showcase-example-usage']/table/tbody/tr/td)[4]/input[contains(@id,':selectManyCheckbox')]";
}
