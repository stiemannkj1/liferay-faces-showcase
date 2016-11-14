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
package com.liferay.faces.test.showcase.multimediaimage;

import org.junit.Test;

import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;
import com.liferay.faces.test.showcase.output.OutputTester;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public class ImageGeneralTester extends OutputTester {

	@Test
	public void runImageGeneralTest() throws Exception {

		Browser browser = Browser.getInstance();
		navigateToUseCase(browser, "graphicimage", "general");

		// Test that the images render on the page successfully.
		SeleniumAssert.assertElementVisible(browser,
			"(//img[contains(@src,'.showcase.webapp/javax.faces.resource/') or contains(@src,'showcaseportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_1_WAR_comliferayfacesdemo')])/ancestor::div[contains(@class,'showcase-example')]/label[contains(@class,'showcase-example-label')]/*/a[contains(text(),'value')]");
		SeleniumAssert.assertElementVisible(browser,
			"(//img[contains(@src,'.showcase.webapp/javax.faces.resource/') or contains(@src,'showcaseportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_1_WAR_comliferayfacesdemo')])/ancestor::div[contains(@class,'showcase-example')]/label[contains(@class,'showcase-example-label')]/*/code[contains(text(),'#{resource}')]");
		SeleniumAssert.assertElementVisible(browser,
			"(//img[contains(@src,'.showcase.webapp/javax.faces.resource/') or contains(@src,'showcaseportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_1_WAR_comliferayfacesdemo')])/ancestor::div[contains(@class,'showcase-example')]/label[contains(@class,'showcase-example-label')]/*/a[contains(text(),'name')]");
		SeleniumAssert.assertElementVisible(browser,
			"(//img[contains(@src,'.showcase.webapp/javax.faces.resource/') or contains(@src,'showcaseportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_1_WAR_comliferayfacesdemo')])/ancestor::div[contains(@class,'showcase-example')]/label[contains(@class,'showcase-example-label')]/*/a[contains(text(),'usemap')]");

		// Click the image links on both areas of the example 4 image usemap and check that it opens a new window/tab
		// with the correct domain name.
		testLink(browser, "(//div[contains(@class,'showcase-example-usage')]/*/area[contains(@title,'JSR 362')])",
			"https://jcp.org/en/jsr/detail?id=362");
		testLink(browser, "(//div[contains(@class,'showcase-example-usage')]/*/area[contains(@title,'JSR 378')])",
			"https://www.jcp.org/en/jsr/detail?id=378");
	}
}
