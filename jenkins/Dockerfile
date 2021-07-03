FROM jenkins/jenkins:2.289.2-lts-slim

USER root

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Duser.timezone=Asia/Tokyo -Dorg.apache.commons.jelly.tags.fmt.timeZone=Asia/Tokyo"
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml

RUN apt-get update -y
RUN apt-get install -y vim

COPY ./jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

COPY ./jenkins/casc.yaml $CASC_JENKINS_CONFIG

COPY ./jenkins/init.groovy.d/*.groovy /usr/share/jenkins/ref/init.groovy.d/

USER jenkins
