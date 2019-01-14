#!/bin/sh

if [[ "${AppDFlag}" == "true" ]]; then 
    JAVA_OPTS="-javaagent:/apps/appdynamics/appdagent/javaagent.jar -Dappdynamics.controller.hostName=${Hostname} -Dappdynamics.controller.port=${AppDPort} -Dappdynamics.controller.ssl.enabled=true -Dappdynamics.agent.applicationName=DigitalX-${Environment} -Dappdynamics.agent.tierName=${MSNAME} -Dappdynamics.agent.nodeName=${MSNAME} -Dappdynamics.agent.reuse.nodeName=true -Dappdynamics.agent.reuse.nodeName.prefix=${MSNAME} -Dappdynamics.jvm.shutdown.mark.node.as.historical=true -Dappdynamics.agent.accountAccessKey=${AppDKey} -Xmx${XMX} -Xms${XMS}"
else
    JAVA_OPTS="-Xmx${XMX} -Xms${XMS}"
fi

java $JAVA_OPTS -jar /apps/sb/${MSNAME}-svc-${VERSION}.jar