FROM jenkins/jenkins:2.289.2-lts-slim

USER root

ENV JAVA_OPTS="-Duser.timezone=Asia/Tokyo -Dorg.apache.commons.jelly.tags.fmt.timeZone=Asia/Tokyo"

RUN apt-get update -y
RUN apt-get install -y vim

USER jenkins
