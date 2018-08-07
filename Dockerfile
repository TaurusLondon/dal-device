FROM openjdk:8-jre-alpine

EXPOSE 8080

ARG MSNAME
ENV MSNAME=$MSNAME
ARG VERSION
ENV VERSION=$VERSION
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

RUN mkdir -p /apps/appdynamics/ \
        mkdir -p /apps/stub-files

ADD appdagent.tar /apps/appdynamics/
COPY $MSNAME-svc-$VERSION.jar /apps/sb/

CMD java $JAVA_OPTS -jar /apps/sb/$MSNAME-svc-$VERSION.jar