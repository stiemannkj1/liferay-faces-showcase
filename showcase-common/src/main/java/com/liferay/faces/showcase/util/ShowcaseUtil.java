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
package com.liferay.faces.showcase.util;

import java.util.regex.Pattern;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;


/**
 * @author  Neil Griffin
 */
public class ShowcaseUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ShowcaseUtil.class);

	// Private Constants
	private static final String ANCHOR_ELEMENT_CLOSE = "</a>";
	private static final String ANCHOR_ELEMENT_OPEN_START = "<a href=\"";
	private static final String ANCHOR_ELEMENT_OPEN_FINISH = "\" target=\"_blank\">";
	private static final Pattern BRACKET_DELIMITER_PATTERN = Pattern.compile("[\\[\\]]");
	private static final Pattern COLON_DELIMITER_PATTERN = Pattern.compile(":");
	private static final String HTML_EXTENSION = ".html";
	private static final String JAVADOC_PREFIX = "javadoc:";
	private static final String JAVA_PACKAGE_PREFIX = "java.";
	private static final String JAVAX_PACKAGE_PREFIX = "javax.";
	private static final String LIFERAY_FACES_ALLOY_PACKAGE_PREFIX = "com.liferay.faces.alloy";
	private static final String LIFERAY_FACES_BRIDGE_PACKAGE_PREFIX = "com.liferay.faces.bridge";
	private static final String LIFERAY_FACES_PORTAL_PACKAGE_PREFIX = "com.liferay.faces.portal";
	private static final String LIFERAY_FACES_PORTLET_PACKAGE_PREFIX = "com.liferay.faces.portlet";
	private static final String LIFERAY_FACES_UTIL_PACKAGE_PREFIX = "com.liferay.faces.util";
	private static final String LIFERAY_FACES_VDLDOC_BASE_URL = "http://www.liferayfaces.org/doc/faces/";
	private static final String NAMESPACE_PREFIX_ALLOY = "alloy";
	private static final String NAMESPACE_PREFIX_AUI = "aui";
	private static final String NAMESPACE_PREFIX_BRIDGE = "bridge";
	private static final String NAMESPACE_PREFIX_C = "c";
	private static final String NAMESPACE_PREFIX_F = "f";
	private static final String NAMESPACE_PREFIX_H = "h";
	private static final String NAMESPACE_PREFIX_LIFERAY_UI = "liferay-ui";
	private static final String NAMESPACE_PREFIX_PORTLET = "portlet";
	private static final String NAMESPACE_PREFIX_PORTAL = "portal";
	private static final String NAMESPACE_PREFIX_UI = "ui";
	private static final String PORTLET_API_PACKAGE_PREFIX = "javax.portlet";
	private static final String PORTLET_API_JAVADOC_BASE_URL = "http://docs.liferay.com/portlet-api/2.0/javadocs";
	private static final String REGEX_COLON = "[:]";
	private static final String REGEX_DOT = "[.]";
	private static final String SINGLE_BACKSLASH_COLON = "\\\\:";
	private static final String STRONG_OPEN = "<strong>";
	private static final String STRONG_CLOSE = "</strong>";
	private static final String VDLDOC_PREFIX = "vdldoc:";

	public static Integer columnUnitSize(Integer width) {
		return (int) Math.round(12 * ((double) width / 100));
	}

	public static String doubleEscapeClientId(String clientId) {
		return ComponentUtil.escapeClientId(clientId);
	}

	public static final String encodeDescription(FacesContext facesContext, String description) {

		String encodedDescription = description;

		if (description != null) {

			StringBuilder stringBuilder = new StringBuilder();
			String[] parts = BRACKET_DELIMITER_PATTERN.split(description);

			for (String part : parts) {

				if (part.startsWith(JAVADOC_PREFIX)) {
					stringBuilder.append(ANCHOR_ELEMENT_OPEN_START);

					JavaDocKey javaDocKey = new JavaDocKey(part);
					String javaDocURL = encodeJavaDocURL(facesContext, javaDocKey);
					stringBuilder.append(javaDocURL);
					stringBuilder.append(ANCHOR_ELEMENT_OPEN_FINISH);
					stringBuilder.append(javaDocKey.getClassName());

					String fragment = javaDocKey.getFragment();

					if (fragment != null) {
						stringBuilder.append(".");
						stringBuilder.append(fragment);
					}

					stringBuilder.append(ANCHOR_ELEMENT_CLOSE);
				}
				else if (part.startsWith(VDLDOC_PREFIX)) {

					stringBuilder.append(ANCHOR_ELEMENT_OPEN_START);

					VDLDocKey vdlDocKey = new VDLDocKey(part);
					String vdlDocURL = encodeVDLDocURL(facesContext, vdlDocKey);
					stringBuilder.append(vdlDocURL);
					stringBuilder.append(ANCHOR_ELEMENT_OPEN_FINISH);

					String attributeName = vdlDocKey.getAttributeName();

					if (attributeName == null) {
						stringBuilder.append(vdlDocKey.getTagPrefix());
						stringBuilder.append(":");
						stringBuilder.append(vdlDocKey.getTagName());
					}
					else {
						stringBuilder.append(attributeName);
					}

					stringBuilder.append(ANCHOR_ELEMENT_CLOSE);
				}
				else {
					stringBuilder.append(encodeSourceCode(encodeStrong(encodeWarning(part))));
				}
			}

			encodedDescription = stringBuilder.toString();
		}

		return encodedDescription;
	}

	public static final String encodeJavaDocURL(String javaDocKey) {
		return encodeJavaDocURL(FacesContext.getCurrentInstance(), new JavaDocKey(javaDocKey));
	}

	public static final String encodeJavaDocURL(FacesContext facesContext, JavaDocKey javaDocKey) {

		StringBuilder javaDocURL = new StringBuilder();

		String fqcn = javaDocKey.getFQCN();

		if (fqcn.startsWith(JAVA_PACKAGE_PREFIX)) {

			ExternalContext externalContext = facesContext.getExternalContext();
			final ProductInfo JSF = ProductInfoFactory.getProductInfoInstance(externalContext, ProductInfo.Name.JSF);

			if (JSF.getMajorVersion() >= 2) {
				javaDocURL.append("http://docs.oracle.com/javase/7/docs/api");
			}
			else {
				javaDocURL.append("http://docs.oracle.com/javase/6/docs/api");
			}
		}
		else if (fqcn.startsWith(PORTLET_API_PACKAGE_PREFIX)) {
			javaDocURL.append(PORTLET_API_JAVADOC_BASE_URL);
		}
		else if (fqcn.startsWith(JAVAX_PACKAGE_PREFIX)) {

			ExternalContext externalContext = facesContext.getExternalContext();
			final ProductInfo JSF = ProductInfoFactory.getProductInfoInstance(externalContext, ProductInfo.Name.JSF);

			if (JSF.getMajorVersion() >= 2) {
				javaDocURL.append("http://docs.oracle.com/javaee/7/api");
			}
			else {
				javaDocURL.append("http://docs.oracle.com/javaee/6/api");
			}
		}
		else if (fqcn.startsWith(LIFERAY_FACES_ALLOY_PACKAGE_PREFIX)) {

			javaDocURL.append("http://www.liferayfaces.org/doc/faces/javadoc/alloy/");
			javaDocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_ALLOY));
		}
		else if (fqcn.startsWith(LIFERAY_FACES_BRIDGE_PACKAGE_PREFIX) ||
				fqcn.startsWith(LIFERAY_FACES_PORTLET_PACKAGE_PREFIX)) {

			javaDocURL.append("http://www.liferayfaces.org/doc/faces/javadoc/bridge-api/");
			javaDocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_BRIDGE));
		}
		else if (fqcn.startsWith(LIFERAY_FACES_PORTAL_PACKAGE_PREFIX)) {

			javaDocURL.append("http://www.liferayfaces.org/doc/faces/javadoc/portal/");
			javaDocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_PORTAL));
		}
		else if (fqcn.startsWith(LIFERAY_FACES_UTIL_PACKAGE_PREFIX)) {

			javaDocURL.append("http://www.liferayfaces.org/doc/faces/javadoc/util/");
			javaDocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_UTIL));
		}
		else {
			logger.error("Unknown JavaDoc fqcn=[{0}]", fqcn);
		}

		javaDocURL.append("/");

		String javaClassURLPath = fqcn.replaceAll(REGEX_DOT, "/");
		javaDocURL.append(javaClassURLPath);
		javaDocURL.append(HTML_EXTENSION);

		String fragment = javaDocKey.getFragment();

		if (fragment != null) {

			javaDocURL.append("#");
			javaDocURL.append(fragment);
		}

		return javaDocURL.toString();
	}

	public static final String encodeSourceCode(String text) {

		if (text != null) {

			boolean openTag = true;

			int pos = text.indexOf("`");

			while (pos >= 0) {

				if (openTag) {
					text = text.substring(0, pos) + "<code>" + text.substring(pos + 1);
				}
				else {
					text = text.substring(0, pos) + "</code>" + text.substring(pos + 1);
				}

				pos = text.indexOf("`");
				openTag = !openTag;
			}
		}

		return text;
	}

	public static final String encodeStrong(String text) {

		if (text != null) {

			boolean openTag = true;

			int pos = text.indexOf("*");

			while (pos >= 0) {

				if (openTag) {
					text = text.substring(0, pos) + STRONG_OPEN + text.substring(pos + 1);
				}
				else {
					text = text.substring(0, pos) + STRONG_CLOSE + text.substring(pos + 1);
				}

				pos = text.indexOf("*");
				openTag = !openTag;
			}
		}

		return text;
	}

	public static final String encodeVDLDocURL(String vdlDocKey) {
		return encodeVDLDocURL(FacesContext.getCurrentInstance(), new VDLDocKey(vdlDocKey));
	}

	public static final String encodeVDLDocURL(FacesContext facesContext, VDLDocKey vdlDocKey) {

		StringBuilder vdldocURL = new StringBuilder();

		String tagPrefix = vdlDocKey.getTagPrefix();

		if (tagPrefix.equals(NAMESPACE_PREFIX_C) || tagPrefix.equals(NAMESPACE_PREFIX_F) ||
				tagPrefix.equals(NAMESPACE_PREFIX_H) || tagPrefix.equals(NAMESPACE_PREFIX_UI)) {

			vdldocURL.append("https://javaserverfaces.java.net/nonav/docs/");
			vdldocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.JSF));
			vdldocURL.append("/vdldocs/facelets/");
		}
		else if (tagPrefix.equals(NAMESPACE_PREFIX_ALLOY) || tagPrefix.equals(NAMESPACE_PREFIX_AUI) ||
				tagPrefix.equals(NAMESPACE_PREFIX_BRIDGE) || tagPrefix.equals(NAMESPACE_PREFIX_LIFERAY_UI) ||
				tagPrefix.equals(NAMESPACE_PREFIX_PORTLET) || tagPrefix.equals(NAMESPACE_PREFIX_PORTAL)) {

			vdldocURL.append(LIFERAY_FACES_VDLDOC_BASE_URL);
			vdldocURL.append("vdldoc/");

			if (tagPrefix.equals(NAMESPACE_PREFIX_ALLOY)) {

				vdldocURL.append(NAMESPACE_PREFIX_ALLOY);
				vdldocURL.append("/");
				vdldocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_ALLOY));
			}
			else if (tagPrefix.equals(NAMESPACE_PREFIX_BRIDGE) || tagPrefix.equals(NAMESPACE_PREFIX_PORTLET)) {

				vdldocURL.append(NAMESPACE_PREFIX_BRIDGE);
				vdldocURL.append("-api/");
				vdldocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_BRIDGE));
			}
			else if (tagPrefix.equals(NAMESPACE_PREFIX_PORTAL)) {

				vdldocURL.append(NAMESPACE_PREFIX_PORTAL);
				vdldocURL.append("/");
				vdldocURL.append(getMajorDotMinorVersion(facesContext, ProductInfo.Name.LIFERAY_FACES_PORTAL));
			}

			vdldocURL.append("/");
		}
		else {
			logger.error("Unknown VDLDoc tagPrefix=[{0}]", tagPrefix);
		}

		vdldocURL.append(tagPrefix);
		vdldocURL.append("/");

		String tagName = vdlDocKey.getTagName();
		vdldocURL.append(tagName);
		vdldocURL.append(HTML_EXTENSION);

		String attributeName = vdlDocKey.getAttributeName();

		if (attributeName != null) {
			vdldocURL.append("#");
			vdldocURL.append(attributeName);
		}

		return vdldocURL.toString();
	}

	public static final String encodeWarning(String text) {

		if (text != null) {

			boolean openTag = true;

			int pos = text.indexOf("!");

			while (pos >= 0) {

				if (openTag) {
					text = text.substring(0, pos) + "<span class=\"inline-warning\">" + text.substring(pos + 1);
				}
				else {
					text = text.substring(0, pos) + "</span>" + text.substring(pos + 1);
				}

				pos = text.indexOf("!");
				openTag = !openTag;
			}
		}

		return text;
	}

	public static String singleEscapeClientId(String clientId) {
		String escapedClientId = clientId;

		if (escapedClientId != null) {

			// JSF clientId values contain colons, which must be preceeded by single backslashes in order to have them
			// work with Bootstrap+jQuery functions.
			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, SINGLE_BACKSLASH_COLON);
		}

		return escapedClientId;
	}

	private static String getMajorDotMinorVersion(FacesContext facesContext, ProductInfo.Name productInfoName) {

		ExternalContext externalContext = facesContext.getExternalContext();
		ProductInfo productInfo = ProductInfoFactory.getProductInfoInstance(externalContext, productInfoName);
		String majorDotMinorVersion;

		if (ProductInfo.Name.JSF.equals(productInfoName) && (productInfo.getMajorVersion() < 2)) {
			majorDotMinorVersion = "2.0";
		}
		else {
			majorDotMinorVersion = productInfo.getMajorVersion() + "." + productInfo.getMinorVersion();
		}

		return majorDotMinorVersion;
	}

	private static class JavaDocKey {

		private String className;
		private String fqcn;
		private String fragment;

		public JavaDocKey(String key) {

			if (key != null) {

				String[] keyParts = COLON_DELIMITER_PATTERN.split(key);

				if (keyParts.length > 1) {

					if (keyParts[1].contains("#")) {

						String[] fqcnAndFragment = keyParts[1].split("#");
						this.fqcn = fqcnAndFragment[0];
						this.fragment = fqcnAndFragment[1];
					}
					else {
						this.fqcn = keyParts[1];
					}

					int pos = this.fqcn.lastIndexOf(".");

					if (pos > 0) {
						className = this.fqcn.substring(pos + 1);
					}
				}
			}
		}

		public String getClassName() {
			return className;
		}

		public String getFQCN() {
			return fqcn;
		}

		public String getFragment() {
			return fragment;
		}
	}

	private static class VDLDocKey {

		private String tagPrefix;
		private String tagName;
		private String attributeName;

		public VDLDocKey(String key) {

			if (key != null) {
				String[] keyParts = COLON_DELIMITER_PATTERN.split(key);

				if (keyParts.length > 1) {
					this.tagPrefix = keyParts[1];
				}

				if (keyParts.length > 2) {
					this.tagName = keyParts[2];
				}

				if (keyParts.length > 3) {
					attributeName = keyParts[3];
				}
			}
		}

		public String getAttributeName() {
			return attributeName;
		}

		public String getTagName() {
			return tagName;
		}

		public String getTagPrefix() {
			return tagPrefix;
		}
	}
}
