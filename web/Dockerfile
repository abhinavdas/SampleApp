FROM centos:centos6
MAINTAINER alana.anderson@appdynamics.com

# Install Node.js 4.2.1
RUN yum install -y wget
RUN wget --no-check-certificate https://nodejs.org/dist/v4.2.1/node-v4.2.1-linux-x64.tar.gz
RUN cd /usr/local
RUN tar --strip-components 1 -xzf node-v4.2.1-linux-x64.tar.gz && rm node-v4.2.1-linux-x64.tar.gz

# Install OS packages
RUN yum install -y git unzip rsyslog

RUN mkdir -p  SampleApp
ADD src /SampleApp/src
ADD package.json /SampleApp/package.json
RUN cd SampleApp && npm install

ADD install-appdynamics.sh /usr/local/bin/install-appdynamics
ADD start-all.sh /usr/local/bin/start-all

RUN chmod +x /usr/local/bin/install-appdynamics
RUN chmod +x /usr/local/bin/start-all

# Tail syslog to keep container running
CMD install-appdynamics && start-all && tail -f /var/log/messages

EXPOSE 3000
