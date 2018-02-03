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
package com.liferay.faces.showcase.application.internal;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ResourceUtil;
import com.liferay.faces.util.application.ResourceVerifier;
import com.liferay.faces.util.application.ResourceVerifierWrapper;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;


/**
 * @author  Kyle Stiemann
 */
public class ResourceVerifierShowcaseImpl extends ResourceVerifierWrapper implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4691201895912598647L;

	// Private Constants
	private static final String BOOTSTRAP_RESOURCE_ID = ResourceUtil.getResourceId("bootstrap",
			"css/bootstrap.min.css");
	private static final String JQUERY_JS_RESOURCE_ID = ResourceUtil.getResourceId("bootstrap", "js/jquery.min.js");

	// Private Members
	private ResourceVerifier wrappedResourceDependencyHandler;

	public ResourceVerifierShowcaseImpl(ResourceVerifier resourceVerifier) {
		this.wrappedResourceDependencyHandler = resourceVerifier;
	}

	private static boolean isBootstrapDependencySatisfied(ProductInfoFactory productInfoFactory,
		final ProductInfo LIFERAY_PORTAL) {

		final ProductInfo LIFERAY_FACES_ALLOY = productInfoFactory.getProductInfo(ProductInfo.Name.LIFERAY_FACES_ALLOY);
		final ProductInfo LIFERAY_FACES_CLAY = productInfoFactory.getProductInfo(ProductInfo.Name.LIFERAY_FACES_CLAY);

		return (LIFERAY_FACES_ALLOY.isDetected() || LIFERAY_PORTAL.isDetected() || LIFERAY_FACES_CLAY.isDetected());
	}

	private static boolean isJQueryDependencySatisfied(final ProductInfo LIFERAY_PORTAL) {
		return (LIFERAY_PORTAL.isDetected() && (LIFERAY_PORTAL.getMajorVersion() >= 7));
	}

	@Override
	public ResourceVerifier getWrapped() {
		return wrappedResourceDependencyHandler;
	}

	@Override
	public boolean isDependencySatisfied(FacesContext facesContext, UIComponent componentResource) {

		String resourceId = ResourceUtil.getResourceId(componentResource);
		ExternalContext externalContext = facesContext.getExternalContext();
		ProductInfoFactory productInfoFactory = (ProductInfoFactory) FactoryExtensionFinder.getFactory(externalContext,
				ProductInfoFactory.class);
		final ProductInfo LIFERAY_PORTAL = productInfoFactory.getProductInfo(ProductInfo.Name.LIFERAY_PORTAL);

		if (isBootstrapDependencySatisfied(productInfoFactory, LIFERAY_PORTAL) &&
				BOOTSTRAP_RESOURCE_ID.equals(resourceId)) {
			return true;
		}
		else if (isJQueryDependencySatisfied(LIFERAY_PORTAL) && JQUERY_JS_RESOURCE_ID.equals(resourceId)) {
			return true;
		}
		else {
			return super.isDependencySatisfied(facesContext, componentResource);
		}
	}
}
