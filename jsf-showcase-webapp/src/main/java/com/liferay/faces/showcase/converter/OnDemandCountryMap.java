/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.showcase.converter;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import com.liferay.faces.showcase.dto.Country;
import com.liferay.faces.showcase.service.CountryService;
import com.liferay.faces.util.lang.OnDemand;


/**
 * @author  Kyle Stiemann
 */
/* package-private */ class OnDemandCountryMap extends OnDemand<Map<Long, Country>, FacesContext> {

	// Package-Private Constants
	/* package-private */ static final OnDemandCountryMap INSTANCE = new OnDemandCountryMap();

	@Override
	protected Map<Long, Country> computeInitialValue(FacesContext facesContext) {

		ELResolver elResolver = facesContext.getApplication().getELResolver();
		ELContext elContext = facesContext.getELContext();
		CountryService countryService = (CountryService) elResolver.getValue(elContext, null, "countryService");

		return countryService.getCountryMap();
	}
}
