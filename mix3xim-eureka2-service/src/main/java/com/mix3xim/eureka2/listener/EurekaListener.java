package com.mix3xim.eureka2.listener;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaEvent;
import com.netflix.discovery.EurekaEventListener;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class EurekaListener implements EurekaEventListener {
    private Logger log = LoggerFactory.getLogger(EurekaListener.class);
    @Override
    public void onEvent(EurekaEvent applicationEvent) {

        // 服务挂掉事件
        if (applicationEvent instanceof EurekaInstanceCanceledEvent) {
            EurekaInstanceCanceledEvent event = (EurekaInstanceCanceledEvent) applicationEvent;
            // 获取当前Eureka实例中的节点信息
            PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext()
                    .getRegistry();
            Applications applications = registry.getApplications();
            List registered = applications.getRegisteredApplications();
            Iterator var4 = registered.iterator();
            while(var4.hasNext()) {
                Application app = (Application)var4.next();
                if(!app.getInstances().isEmpty()) {
                    List<InstanceInfo> instanceInfos =  app.getInstances();
                    for(InstanceInfo instanceInfo : instanceInfos){
                        if (instanceInfo.getInstanceId().equals(event.getServerId())) {
                            log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务：" + instanceInfo.getAppName()
                                    + " 已停止！！！]");
                        }
                    }

                }
            }
            //jdk1.7不支持forEach用法，暂注释
            // 遍历获取已注册节点中与当前失效节点ID一致的节点信息
			/*applications.getRegisteredApplications().forEach((registeredApplication) -> {
				registeredApplication.getInstances().forEach((instance) -> {
					if (instance.getInstanceId().equals(event.getServerId())) {
						log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务：" + instance.getAppName()
								+ " 已停止！！！]");
					}
				});
			});*/
        }
        if (applicationEvent instanceof EurekaInstanceRegisteredEvent) {
            EurekaInstanceRegisteredEvent event = (EurekaInstanceRegisteredEvent) applicationEvent;
            if (event.getInstanceInfo() != null) {
                log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务："
                        + event.getInstanceInfo().getAppName() + " 注册成功！！！]");
            }
        }
        if (applicationEvent instanceof EurekaInstanceRenewedEvent) {
            EurekaInstanceRenewedEvent event = (EurekaInstanceRenewedEvent) applicationEvent;
            if (event.getInstanceInfo() != null) {
                log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [心跳检测服务："
                        + event.getInstanceInfo().getAppName() + " 注册成功！！！]");
            }
        }
        if (applicationEvent instanceof EurekaRegistryAvailableEvent) {
//			EurekaInstanceRenewedEvent event = (EurekaInstanceRenewedEvent) applicationEvent;
            log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务 Aualiable。。]");
        }

    }
}
