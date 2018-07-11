#!/bin/bash

{ [ -n "$1" ] && ls $1 > /dev/null; } ||
	{ echo "Please pass equinox's home directory to the script." && exit 1; }

cd $1
echo "osgi.bundles=$(cd plugins/ && find . -name '*gogo*' -not -name '*source*' -print0 |\
	sed -e 's|\x0./|@start,|g' -e 's|./||g' -e 's|$|@start|')" > plugins/configuration/config.ini

# Set up necessary Jetty files as partially described here:
# http://www.eclipse.org/jetty/documentation/current/framework-jetty-osgi.html#customize-jetty-container
mkdir -p etc
curl -o etc/jetty.xml https://raw.githubusercontent.com/eclipse/jetty.project/jetty-9.4.8.v20171121/jetty-osgi/jetty-osgi-boot/jettyhome/etc/jetty.xml
curl -o etc/jetty-http.xml https://raw.githubusercontent.com/eclipse/jetty.project/jetty-9.4.8.v20171121/jetty-osgi/jetty-osgi-boot/jettyhome/etc/jetty-http.xml
curl -o etc/jetty-deployer.xml https://raw.githubusercontent.com/eclipse/jetty.project/jetty-9.4.8.v20171121/jetty-osgi/jetty-osgi-boot/jettyhome/etc/jetty-deployer.xml

echo "Start equinox with the following command:"
ORG_ECLIPSE_OSGI_JAR=$(cd plugins/ && find . -regex '\./org\.eclipse\.osgi[_.v0-9-]*\.jar' -not -name '*source*' \
	-print0 | head -1 | sed -e 's|./||g')
echo "java -Djetty.home=$1 -Djetty.http.port=8181 -jar plugins/$ORG_ECLIPSE_OSGI_JAR -console"
