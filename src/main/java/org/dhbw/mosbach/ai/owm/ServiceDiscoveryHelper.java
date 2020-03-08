/**
 *
 */
package org.dhbw.mosbach.ai.owm;

import com.orbitz.consul.Consul;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Alexander.Auch
 *
 */
public class ServiceDiscoveryHelper {
    private static final Logger logger = Logger.getLogger("root");

    public static String getServiceUrl(String suffix) {
        String consulUrl = System.getProperty("swarm.consul.url", "");

        if (consulUrl.isBlank()) {
            consulUrl = System.getenv("CONSUL_URL");
            consulUrl = (consulUrl == null) ? "" : consulUrl;
        }

        String serviceUrl = null;

        logger.info("CONSUL URL: " + consulUrl);

        if (!consulUrl.isEmpty()) {
            final Consul client = Consul.builder().withUrl(consulUrl).build();

            final List<ServiceHealth> serviceHealthEntries = client.healthClient().getHealthyServiceInstances("jsoncache")
                    .getResponse();

            logger.info("Resolved Entries:");

            for (final ServiceHealth svch : serviceHealthEntries) {
                final Service service = svch.getService();
                logger.info(String.format("Service: %s, Address: %s, Port: %d", service.getService(), service.getAddress(),
                        Integer.valueOf(service.getPort())));
            }

            if (!serviceHealthEntries.isEmpty()) {
                final Service service = serviceHealthEntries.get(0).getService();

                serviceUrl = String.format("http://%s:%d", service.getAddress(), Integer.valueOf(service.getPort()));
            }

            logger.info("Resolved URL: " + serviceUrl);
        }

        serviceUrl = (serviceUrl != null) ? serviceUrl + suffix : null;

        return serviceUrl;
    }
}
