#!/bin/bash

{ [ -n "$1" ] && ls $1 > /dev/null; } ||
	{ echo "Please pass equinox's home directory to the script." && exit 1; }

cd $1
echo "osgi.bundles=$(cd plugins/ && find . -name '*gogo*' -not -name '*source*' -print0 |\
	sed -e 's|\x0./|@start,|g' -e 's|./||g' -e 's|$|@start|')" > plugins/configuration/config.ini

mkdir -p etc
curl -o etc/jetty.xml https://raw.githubusercontent.com/eclipse/jetty.project/jetty-9.4.8.v20171121/jetty-osgi/jetty-osgi-boot/jettyhome/etc/jetty.xml
curl -o etc/jetty-http.xml https://raw.githubusercontent.com/eclipse/jetty.project/jetty-9.4.8.v20171121/jetty-osgi/jetty-osgi-boot/jettyhome/etc/jetty-http.xml
curl -o etc/jetty-deployer.xml https://raw.githubusercontent.com/eclipse/jetty.project/jetty-9.4.8.v20171121/jetty-osgi/jetty-osgi-boot/jettyhome/etc/jetty-deployer.xml

echo "Start equinox with the following command:"
echo "java -Djetty.home=$1 -Djetty.http.port=8080 -jar plugins/org.eclipse.osgi_3.12.1.v20170821-1548.jar -console"
