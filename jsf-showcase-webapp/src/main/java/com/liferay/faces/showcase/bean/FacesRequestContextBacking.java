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
package com.liferay.faces.showcase.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.util.context.FacesRequestContext;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@ViewScoped
public class FacesRequestContextBacking {

	private String email;

	public void closeDialog() {

		if (email != null) {

			com.liferay.faces.util.context.FacesRequestContext facesRequestContext = FacesRequestContext
				.getCurrentInstance();
			facesRequestContext.addScript("$('.modal').modal('hide');");
		}
	}

	public String getEmail() {
		return email;
	}

	public void openDialog() {

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
		facesRequestContext.addScript("$('.modal').modal('show');");
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
