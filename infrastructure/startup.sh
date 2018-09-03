#!/bin/sh

if [[ -n "${java_opts}" ]]; then 
    java_opts="-javaagent:/apps/appdynamics/appdagent/javaagent.jar -Dappdynamics.controller.hostName=${hostname} -Dappdynamics.controller.port=${appdport} -Dappdynamics.controller.ssl.enabled=true -Dappdynamics.agent.applicationName=DigitalX-pat -Dappdynamics.agent.tierName=${MSNAME} -Dappdynamics.agent.nodeName=${MSNAME} -Dappdynamics.agent.reuse.nodeName=true -Dappdynamics.agent.reuse.nodeName.prefix=${MSNAME} -Dappdynamics.jvm.shut
    down.mark.node.as.historical=true -Dappdynamics.agent.accountAccessKey=${appdkey}"
fi

java ${java_opts} -jar /apps/sb/${MSNAME}-svc-${VERSION}.jar
