ARG FIX_PACK_VERSION
ARG LIBERTY_IMAGE_TYPE=kernel-slim
ARG JAVA_VERSION=21
ARG JAVA_TYPE=openj9
ARG REGISTRY=registry.repository.alm.sas.junta-andalucia.es
ARG DOCKER_IMAGE=alm/docker/sas.base.image.serverruntime.openliberty
ARG OPERATION_SYSTEM=-ubi
FROM icr.io/appcafe/open-liberty:latest

# Para entregar el Dockerfile al SAS VERBOSE=false y el resto de ARGS iran vacios sin indicar el =xxx
ARG VERBOSE=false
ARG EXTERNAL_LIBRARIES_PATH=boot/target/liberty/wlp/usr/shared/resources/*.jar
ARG WAR_PATH=boot/target/*.war
ARG SERVER_XML_PATH=boot/src/main/liberty/config/

ENV JVM_ARGS="-Djava.security.egd=file:/dev/./urandom"

#
#	Creamos carpeta de librerias externas
#
RUN mkdir -p /config/lib
COPY --chown=1001:0  $SERVER_XML_PATH /config/
COPY --chown=1001:0  $WAR_PATH  /config/apps
COPY --chown=1001:0  $EXTERNAL_LIBRARIES_PATH /config/usr/shared/resources/
COPY --chown=1001:0  $EXTERNAL_LIBRARIES_PATH /opt/ol/wlp/usr/shared/resources
COPY --chown=1001:0  boot/src/main/liberty/resources/ca.crt /config/usr/shared/resources/

USER root

RUN keytool -import -trustcacerts -alias sas_ca -keystore /opt/java/openjdk/lib/security/cacerts -file /config/usr/shared/resources/ca.crt -storepass changeit -noprompt

# This script will add the requested server configurations, apply any iFixes and populate caches to optimize runtime
RUN configure.sh

USER 1001

RUN /opt/ol/wlp/bin/server start defaultServer \
    && /opt/ol/wlp/bin/server stop defaultServer --force
