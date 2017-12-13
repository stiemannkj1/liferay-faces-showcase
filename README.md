# Liferay Faces Showcase

This repository contains the source code for the JSF Showcase webapp and the showcase-common utility library. These projects serve as a basis for the [JSF Showcase Portlet](https://github.com/liferay/liferay-faces-bridge-impl/tree/master/demos/jsf-showcase-portlet), [Alloy Showcase Webapp](https://github.com/liferay/liferay-faces-alloy/tree/master/demos/alloy-showcase-webapp), [Alloy Showcase Portlet](https://github.com/liferay/liferay-faces-bridge-impl/tree/master/demos/alloy-showcase-portlet), and [Portal Showcase Portlet](https://github.com/liferay/liferay-faces-portal/tree/master/demos/portal-showcase-portlet).

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

## News

For the latest news and updates, follow [@liferayfaces](https://twitter.com/liferayfaces).

## Building From Source

Using [Maven](https://maven.apache.org/) 3.x:

	mvn clean install

## JSF Showcase -> OSGi + Jetty

```
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-util/9.4.8.v20171121/jetty-util-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-http/9.4.8.v20171121/jetty-http-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-io/9.4.8.v20171121/jetty-io-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-security/9.4.8.v20171121/jetty-security-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-server/9.4.8.v20171121/jetty-server-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-servlet/9.4.8.v20171121/jetty-servlet-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-webapp/9.4.8.v20171121/jetty-webapp-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-deploy/9.4.8.v20171121/jetty-deploy-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-xml/9.4.8.v20171121/jetty-xml-9.4.8.v20171121.jar

install file:./plugins/org.eclipse.osgi.util_3.4.0.v20170111-1608.jar
install file:./plugins/org.eclipse.equinox.console_1.1.300.v20170512-2111.jar
install file:./plugins/org.eclipse.osgi.services_3.6.0.v20170228-1906.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/osgi/jetty-osgi-boot/9.4.8.v20171121/jetty-osgi-boot-9.4.8.v20171121.jar

install file:/home/kylestiemann/.m2/repository/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar
install file:/home/kylestiemann/.m2/repository/org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar
install file:/home/kylestiemann/.m2/repository/org/apache/aries/org.apache.aries.util/1.1.3/org.apache.aries.util-1.1.3.jar
install file:/home/kylestiemann/.m2/repository/org/apache/aries/spifly/org.apache.aries.spifly.dynamic.bundle/1.0.10/org.apache.aries.spifly.dynamic.bundle-1.0.10.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-jndi/9.4.8.v20171121/jetty-jndi-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/javax/transaction/javax.transaction-api/1.2/javax.transaction-api-1.2.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-plus/9.4.8.v20171121/jetty-plus-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/javax/annotation/javax.annotation-api/1.2/javax.annotation-api-1.2.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/jetty-annotations/9.4.8.v20171121/jetty-annotations-9.4.8.v20171121.jar

install file:/home/kylestiemann/.m2/repository/com/liferay/faces/com.liferay.faces.osgi.weaver/1.0.0-SNAPSHOT/com.liferay.faces.osgi.weaver-1.0.0-SNAPSHOT.jar
install file:plugins/javax.el_2.2.0.v201303151357.jar
install file:/home/kylestiemann/.m2/repository/org/glassfish/web/el-impl/2.2/el-impl-2.2.jar
install file:/home/kylestiemann/.m2/repository/org/eclipse/jetty/cdi/cdi-core/9.4.8.v20171121/cdi-core-9.4.8.v20171121.jar
install file:/home/kylestiemann/.m2/repository/javax/interceptor/javax.interceptor-api/1.2.1/javax.interceptor-api-1.2.1.jar
install file:/home/kylestiemann/.m2/repository/org/jboss/weld/weld-osgi-bundle/1.1.34.Final/weld-osgi-bundle-1.1.34.Final.jar
install file:plugins/javax.servlet.jsp_2.2.0.v201112011158.jar
install file:plugins/org.apache.jasper.glassfish_2.2.2.v201501141630.jar
install file:/home/kylestiemann/.m2/repository/javax/servlet/jsp/jstl/javax.servlet.jsp.jstl-api/1.2.1/javax.servlet.jsp.jstl-api-1.2.1.jar

install file:/home/kylestiemann/.m2/repository/javax/validation/validation-api/1.1.0.Final/validation-api-1.1.0.Final.jar
install file:/home/kylestiemann/.m2/repository/com/fasterxml/classmate/0.8.0/classmate-0.8.0.jar
install file:/home/kylestiemann/.m2/repository/org/jboss/logging/jboss-logging/3.3.0.Final/jboss-logging-3.3.0.Final.jar
install file:/home/kylestiemann/.m2/repository/org/hibernate/hibernate-validator/5.1.3.Final/hibernate-validator-5.1.3.Final.jar

install file:/home/kylestiemann/.m2/repository/org/glassfish/javax.faces/2.2.16-SNAPSHOT/javax.faces-2.2.16-SNAPSHOT.jar
install file:/home/kylestiemann/.m2/repository/com/liferay/faces/com.liferay.faces.util/3.2.0-SNAPSHOT/com.liferay.faces.util-3.2.0-SNAPSHOT.jar

install file:/home/kylestiemann/.m2/repository/commons-io/commons-io/2.2/commons-io-2.2.jar
install file:/home/kylestiemann/.m2/repository/commons-fileupload/commons-fileupload/1.3.1/commons-fileupload-1.3.1.jar
```

## Documentation

* [Official Documentation](http://www.liferay.com/community/liferay-projects/liferay-faces/documentation)
* [Liferay Faces Wiki](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+Faces)

## Community Participation

Please post questions in the [Liferay Faces Forum](http://www.liferay.com/community/forums/-/message_boards/category/13289027).
Defects and feature requests can be posted in the [Liferay Faces Issue Tracker](http://issues.liferay.com/browse/FACES).
