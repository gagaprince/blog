#!/bin/bash

source /etc/profile

echo "begin rebuild"

echo $PATH

env

cd /root/work/blog/

ls

git pull

mvn clean -Paliyun package

rm -rf /usr/share/tomcat7/webapps/blog

rm -rf /usr/share/tomcat7/webapps/blog.war

rm -rf /usr/share/tomcat7/webapps/usr#share#tomcat7#webapps

killall -9 java

#mv target/blog.war /usr/share/tomcat7/webapps/

mv target/blog /usr/share/tomcat7/webapps/blog

ls /usr/share/tomcat7/webapps/

echo 'kill blog'

#ps -ef|grep tomcat7 |grep -v grep|awk '{print $2}'|xargs kill -9

sh /usr/share/tomcat7/bin/startup.sh 
