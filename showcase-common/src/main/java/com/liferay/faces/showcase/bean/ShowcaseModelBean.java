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
package com.liferay.faces.showcase.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.showcase.dto.SelectedComponent;
import com.liferay.faces.showcase.dto.SelectedComponentImpl;
import com.liferay.faces.showcase.dto.ShowcaseComponent;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class ShowcaseModelBean implements Serializable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ShowcaseModelBean.class);

	// serialVersionUID
	private static final long serialVersionUID = 3339667513222866249L;

	// Injections
	@ManagedProperty(name = "listModelBean", value = "#{listModelBean}")
	private transient ListModelBean listModelBean;

	// Private Data Members
	private String deploymentType;
	private SelectedComponent selectedComponent;
	private String sourceControlURL;
	private ViewParameters viewParameters;

	public static boolean isBootstrap2(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();
		ProductFactory productFactory = (ProductFactory) FactoryExtensionFinder.getFactory(externalContext,
				ProductFactory.class);
		final Product LIFERAY_PORTAL = productFactory.getProductInfo(Product.Name.LIFERAY_PORTAL);
		final Product ALLOY = productFactory.getProductInfo(Product.Name.LIFERAY_FACES_ALLOY);
		final boolean LIFERAY_PORTAL_6_2_DETECTED = LIFERAY_PORTAL.isDetected() &&
			((LIFERAY_PORTAL.getMajorVersion() == 6) && (LIFERAY_PORTAL.getMinorVersion() == 2));
		final boolean ALLOY_2_DETECTED = ALLOY.isDetected() && (ALLOY.getMajorVersion() == 2);

		return (ALLOY_2_DETECTED || LIFERAY_PORTAL_6_2_DETECTED);
	}

	public String getDeploymentType() {

		if (deploymentType == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = facesContext.getExternalContext();
			final Product LIFERAY_FACES_BRIDGE = ProductFactory.getProductInstance(externalContext,
					Product.Name.LIFERAY_FACES_BRIDGE);

			if (LIFERAY_FACES_BRIDGE.isDetected()) {
				deploymentType = "portlet";
			}
			else {
				deploymentType = "webapp";
			}
		}

		return deploymentType;
	}

	public SelectedComponent getSelectedComponent() {

		if (selectedComponent == null) {

			ViewParameters viewParameters = getViewParameters();

			if (viewParameters.isValid()) {

				ShowcaseComponent showcaseComponent = listModelBean.findShowcaseComponent(
						viewParameters.getComponentPrefix(), viewParameters.getComponentName());

				selectedComponent = new SelectedComponentImpl(showcaseComponent, viewParameters.getComponentUseCase());

				logger.debug("{0}:{1} useCase=[{2}]", viewParameters.getComponentPrefix(),
					viewParameters.getComponentName(), viewParameters.getComponentUseCase());
			}
		}

		return selectedComponent;
	}

	public String getSourceControlURL() {

		if (sourceControlURL == null) {

			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("https://github.com/liferay/liferay-faces-");

			String selectedComponentPrefix = selectedComponent.getPrefix();
			String componentRepoSuffix = selectedComponentPrefix;
			String componentShowcaseFolderPrefix = selectedComponentPrefix;
			String componentShowcaseContainer = "webapp";

			if ("c".equals(selectedComponentPrefix) || "f".equals(selectedComponentPrefix) ||
					"h".equals(selectedComponentPrefix) || "util".equals(selectedComponentPrefix) ||
					"ui".equals(selectedComponentPrefix)) {

				componentRepoSuffix = "showcase";
				componentShowcaseFolderPrefix = "jsf";
			}
			else if ("bridge".equals(selectedComponentPrefix) || "portlet".equals(selectedComponentPrefix)) {

				componentRepoSuffix = "bridge-impl";
				componentShowcaseFolderPrefix = "jsf";
				componentShowcaseContainer = "portlet";
			}
			else if ("portal".equals(selectedComponentPrefix)) {
				componentShowcaseContainer = "portlet";
			}

			urlBuilder.append(componentRepoSuffix);
			urlBuilder.append("/edit/master/");

			if (!"showcase".equals(componentRepoSuffix)) {
				urlBuilder.append("demo/");
			}

			urlBuilder.append(componentShowcaseFolderPrefix);
			urlBuilder.append("-showcase-");
			urlBuilder.append(componentShowcaseContainer);
			urlBuilder.append("/src/main/webapp/WEB-INF/component/");
			urlBuilder.append(selectedComponentPrefix);
			urlBuilder.append("/");
			urlBuilder.append(selectedComponent.getLowerCaseName());
			urlBuilder.append("/");
			urlBuilder.append(selectedComponent.getUseCaseName());
			urlBuilder.append("/");
			urlBuilder.append(selectedComponent.getCamelCaseName());
			urlBuilder.append(".xhtml");

			sourceControlURL = urlBuilder.toString();

		}

		return sourceControlURL;
	}

	public ViewParameters getViewParameters() {

		if (viewParameters == null) {
			viewParameters = new ViewParameters();
		}

		return viewParameters;
	}

	public boolean isBootstrap2() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		return isBootstrap2(facesContext);
	}

	public void setListModelBean(ListModelBean listModelBean) {
		this.listModelBean = listModelBean;
	}

	public static class ViewParameters implements Serializable {

		// serialVersionUID
		private static final long serialVersionUID = 1629675419430845173L;

		// Private Data Members
		private String componentPrefix;
		private String componentName;
		private String componentUseCase;

		public String getComponentName() {
			return componentName;
		}

		public String getComponentPrefix() {
			return componentPrefix;
		}

		public String getComponentUseCase() {
			return componentUseCase;
		}

		public boolean isValid() {
			return ((componentPrefix != null) && (componentName != null));
		}

		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}

		public void setComponentPrefix(String componentPrefix) {
			this.componentPrefix = componentPrefix;
		}

		public void setComponentUseCase(String componentUseCase) {
			this.componentUseCase = componentUseCase;
		}
	}
}
