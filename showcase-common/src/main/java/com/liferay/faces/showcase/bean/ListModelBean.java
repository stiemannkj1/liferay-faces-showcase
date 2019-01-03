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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.faces.application.ProjectStage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import com.liferay.faces.showcase.dto.CodeExample;
import com.liferay.faces.showcase.dto.ShowcaseComponent;
import com.liferay.faces.showcase.dto.ShowcaseComponentComparator;
import com.liferay.faces.showcase.dto.ShowcaseComponentImpl;
import com.liferay.faces.showcase.dto.UseCase;
import com.liferay.faces.showcase.util.CodeExampleUtil;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = ListModelBean.BEAN_NAME, eager = true)
@ApplicationScoped
public class ListModelBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ListModelBean.class);

	// Public Constants
	public static final String BEAN_NAME = "listModelBean";

	// Private Constants
	private static final String[] PACKAGE_NAMES = new String[] {
			"com.liferay.faces.bridge.demos.bean", "com.liferay.faces.showcase.bean",
			"com.liferay.faces.showcase.constants", "com.liferay.faces.showcase.dto",
			"com.liferay.faces.showcase.converter", "com.liferay.faces.showcase.model",
			"com.liferay.faces.showcase.portlet", "com.liferay.faces.showcase.validator",
			"com.liferay.faces.showcase.service", "com.liferay.faces.bridge.demos.portlet"
		};

	// Private Data Members
	private List<String> showcaseCategoryList;
	private List<ShowcaseComponent> showcaseComponents;
	private Map<String, List<ShowcaseComponent>> showcaseCategoryMap;
	private Map<String, ShowcaseComponent> showcaseComponentMap;
	private String dependencyInfo;

	public ShowcaseComponent findShowcaseComponent(String prefix, String name) {
		String key = prefix + "_" + name;

		return showcaseComponentMap.get(key);
	}

	public String getDependencyInfo() {
		return dependencyInfo;
	}

	public List<String> getShowcaseCategories() {
		return showcaseCategoryList;
	}

	public Map<String, List<ShowcaseComponent>> getShowcaseCategoryMap() {
		return showcaseCategoryMap;
	}

	/**
	 * Initialization must occur after the {@link com.liferay.faces.util.config.ApplicationConfig} is constructed
	 * (rather than in the constructor or a {@link javax.annotation.PostConstruct}) to ensure that Util factories have
	 * been initialized. This method is called during {@link
	 * com.liferay.faces.showcase.event.PostConstructApplicationConfigEventListener#processEvent(javax.faces.event.SystemEvent)
	 * }.
	 *
	 * @param  facesContext
	 */
	public void postConstructApplicationConfigEvent(FacesContext facesContext) throws AbortProcessingException {

		boolean developmentMode = facesContext.isProjectStage(ProjectStage.Development);
		boolean productionMode = facesContext.isProjectStage(ProjectStage.Production);
		showcaseCategoryList = new ArrayList<String>();

		ExternalContext externalContext = facesContext.getExternalContext();
		ProductFactory productFactory = (ProductFactory) FactoryExtensionFinder.getFactory(externalContext,
				ProductFactory.class);
		final Product LIFERAY_FACES_PORTAL = productFactory.getProductInfo(Product.Name.LIFERAY_FACES_PORTAL);
		final boolean LIFERAY_FACES_PORTAL_DETECTED = LIFERAY_FACES_PORTAL.isDetected();

		if (LIFERAY_FACES_PORTAL_DETECTED) {
			showcaseCategoryList.add("input");
			showcaseCategoryList.add("misc");
		}
		else {
			showcaseCategoryList.add("buttonlink");
			showcaseCategoryList.add("data");
			showcaseCategoryList.add("input");
			showcaseCategoryList.add("misc");
			showcaseCategoryList.add("multimedia");
			showcaseCategoryList.add("output");
			showcaseCategoryList.add("panel");
			showcaseCategoryList.add("select");
		}

		final Product LIFERAY_FACES_BRIDGE = productFactory.getProductInfo(Product.Name.LIFERAY_FACES_BRIDGE);
		final boolean LIFERAY_FACES_BRIDGE_DETECTED = LIFERAY_FACES_BRIDGE.isDetected();
		final Product LIFERAY_FACES_ALLOY = productFactory.getProductInfo(Product.Name.LIFERAY_FACES_ALLOY);
		final boolean LIFERAY_FACES_ALLOY_DETECTED = LIFERAY_FACES_ALLOY.isDetected();
		final Product LIFERAY_FACES_CLAY = productFactory.getProductInfo(Product.Name.LIFERAY_FACES_CLAY);
		final boolean LIFERAY_FACES_CLAY_DETECTED = LIFERAY_FACES_CLAY.isDetected();

		if (LIFERAY_FACES_BRIDGE_DETECTED && !LIFERAY_FACES_ALLOY_DETECTED && !LIFERAY_FACES_CLAY_DETECTED &&
				!LIFERAY_FACES_PORTAL_DETECTED) {
			showcaseCategoryList.add("portlet");
		}

		showcaseCategoryList.add("jstl");
		showcaseCategoryList.add("facescore");
		showcaseCategoryList.add("facelets");
		showcaseCategoryList.add("extensions");
		showcaseCategoryList = Collections.unmodifiableList(showcaseCategoryList);

		this.showcaseComponents = new ArrayList<ShowcaseComponent>();
		this.showcaseCategoryMap = new HashMap<String, List<ShowcaseComponent>>();
		this.showcaseComponentMap = new HashMap<String, ShowcaseComponent>();

		ClassLoader classLoader = getClass().getClassLoader();

		List<String> namespaces = new ArrayList<String>();
		namespaces.add("alloy");
		namespaces.add("bridge");
		namespaces.add("metal");
		namespaces.add("c");
		namespaces.add("f");
		namespaces.add("h");
		namespaces.add("util");

		if (LIFERAY_FACES_PORTAL_DETECTED) {

			if (developmentMode) {
				namespaces.add("aui");
				namespaces.add("liferay-ui");
			}

			namespaces.add("portal");
		}

		if (LIFERAY_FACES_BRIDGE_DETECTED && !LIFERAY_FACES_ALLOY_DETECTED && !LIFERAY_FACES_CLAY_DETECTED) {
			namespaces.add("portlet");
		}

		namespaces.add("ui");

		String[] namespaceArray = namespaces.toArray(new String[namespaces.size()]);

		for (String namespace : namespaceArray) {

			Properties properties = new Properties();
			String filename;

			if ("util".equals(namespace)) {
				filename = namespace + "-extensions.properties";
			}
			else {
				filename = namespace + "-tags.properties";
			}

			URL resource = classLoader.getResource(filename);

			if (resource == null) {
				namespaces.remove(namespace);
				logger.info("Did not find file: " + filename);
			}
			else {

				try {

					InputStream inputStream = resource.openStream();
					properties.load(inputStream);
					inputStream.close();

					Set<Entry<Object, Object>> entrySet = properties.entrySet();

					for (Map.Entry<Object, Object> mapEntry : entrySet) {
						String key = (String) mapEntry.getKey();

						String[] keyParts = key.split("_");
						String category = keyParts[0];

						String prefix = keyParts[1];
						String camelCaseName = keyParts[2];
						String lowerCaseName = camelCaseName.toLowerCase();

						String value = (String) mapEntry.getValue();
						String[] useCaseArray = value.split("[" + "|" + "]");
						List<UseCase> useCases = new ArrayList<UseCase>(useCaseArray.length);

						for (String useCaseInfo : useCaseArray) {
							String[] useCaseParts = useCaseInfo.split(":");
							String useCaseName = useCaseParts[0];
							String[] sourceFileNames = useCaseParts[1].split(",");
							List<CodeExample> codeExamples = new ArrayList<CodeExample>();

							for (String sourceFileName : sourceFileNames) {

								URL sourceFileURL = null;

								if (sourceFileName.endsWith(".css")) {

									String sourcePath = File.separator + "resources" + File.separator + "css" +
										File.separator + sourceFileName;

									sourceFileURL = externalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".js")) {

									String sourcePath = File.separator + "resources" + File.separator + "js" +
										File.separator + sourceFileName;

									sourceFileURL = externalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".xhtml")) {

									String sourcePath = File.separator + "WEB-INF" + File.separator + "component" +
										File.separator + prefix + File.separator + lowerCaseName + File.separator;

									if (!sourceFileName.toLowerCase().contains("common")) {
										sourcePath = sourcePath + useCaseName + File.separator;
									}

									sourcePath = sourcePath + sourceFileName;
									sourceFileURL = externalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".xml")) {
									String sourcePath = File.separator + "WEB-INF" + File.separator + sourceFileName;
									sourceFileURL = externalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".properties")) {
									sourceFileURL = getClass().getClassLoader().getResource(sourceFileName);
								}
								else {

									for (int i = 0; ((i < PACKAGE_NAMES.length) && (sourceFileURL == null)); i++) {

										int pos = sourceFileName.lastIndexOf(".java");
										String fqcn = PACKAGE_NAMES[i] + "." + sourceFileName.substring(0, pos);

										try {
											Class<?> clazz = Class.forName(fqcn);
											sourceFileURL = clazz.getResource(sourceFileName);
										}
										catch (ClassNotFoundException e) {
											// ignore
										}
									}
								}

								if (sourceFileURL != null) {

									facesContext.getApplication().getProjectStage();

									CodeExample codeExample = CodeExampleUtil.read(sourceFileURL, sourceFileName,
											productionMode);
									codeExamples.add(codeExample);

									logger.debug("Loaded source file=[{0}]", sourceFileName);
								}
								else {
									logger.error(
										"Unable to find source for component=[{0}:{1}] useCaseName=[{2}] sourceFileName=[{3}]",
										prefix, camelCaseName, useCaseName, sourceFileName);
								}
							}

							UseCase useCase = new UseCase(useCaseName, codeExamples);
							useCases.add(useCase);
						}

						int categoryIndex = showcaseCategoryList.indexOf(category);
						ShowcaseComponent showcaseComponent = new ShowcaseComponentImpl(categoryIndex, prefix,
								camelCaseName, lowerCaseName, useCases);
						String lookupKey = prefix + "_" + lowerCaseName;
						this.showcaseComponentMap.put(lookupKey, showcaseComponent);
						this.showcaseComponents.add(showcaseComponent);
					}

					inputStream.close();
				}
				catch (IOException e) {
					logger.error("Unable to load file: " + filename);
				}
			}

			Collections.sort(this.showcaseComponents, new ShowcaseComponentComparator());

			for (int i = 0; i < showcaseCategoryList.size(); i++) {

				List<ShowcaseComponent> categoryShowcaseComponents = new ArrayList<ShowcaseComponent>();

				for (ShowcaseComponent showcaseComponent : this.showcaseComponents) {

					if (i == showcaseComponent.getCategoryIndex()) {
						categoryShowcaseComponents.add(showcaseComponent);
					}
				}

				this.showcaseCategoryMap.put(showcaseCategoryList.get(i), categoryShowcaseComponents);
			}
		}

		showcaseComponents = Collections.unmodifiableList(showcaseComponents);
		showcaseCategoryMap = Collections.unmodifiableMap(showcaseCategoryMap);
		showcaseComponentMap = Collections.unmodifiableMap(showcaseComponentMap);

		boolean previousProductDetected = false;
		StringBuilder buf = new StringBuilder();
		Product[] products = new Product[] {
				LIFERAY_FACES_ALLOY, LIFERAY_FACES_BRIDGE, LIFERAY_FACES_CLAY, LIFERAY_FACES_PORTAL,
				productFactory.getProductInfo(Product.Name.LIFERAY_FACES_SHOWCASE),
				productFactory.getProductInfo(Product.Name.LIFERAY_FACES_UTIL),
				productFactory.getProductInfo(Product.Name.JSF)
			};

		for (Product product : products) {

			if (product.isDetected()) {

				if (previousProductDetected) {
					buf.append(" + ");
				}

				buf.append(product.getTitle());

				String version = product.getVersion();
				int pos = version.indexOf(" ");

				if (pos > 0) {
					version = version.substring(0, pos);
				}

				if (version.length() > 0) {
					buf.append(" ");
					buf.append(version);
				}

				previousProductDetected = true;
			}
		}

		dependencyInfo = buf.toString();
	}
}
