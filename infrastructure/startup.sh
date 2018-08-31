#!/bin/sh

if [[ -n "${java_opts}" ]]; then 
    java_opts="-javaagent:/apps/appdynamics/appdagent/javaagent.jar -Dappdynamics.controller.hostName=$hostname -Dappdynamics.controller.port=$appdport -Dappdynamics.controller.ssl.enabled=true -Dappdynamics.agent.applicationName=DigitalX-PAT -Dappdynamics.agent.tierName=DAL -Dappdynamics.agent.reuse.nodeName=true -Dappdynamics.agent.reuse.nodeName.prefix=$MS_NAME -Dappdynamics.jvm.shutdown.mark.node.as.historical=true -Dappdynamics.agent.accountAccessKey=$appdkey"
fi

java ${java_opts} -jar /apps/sb/${MSNAME}-svc-${VERSION}.jar
