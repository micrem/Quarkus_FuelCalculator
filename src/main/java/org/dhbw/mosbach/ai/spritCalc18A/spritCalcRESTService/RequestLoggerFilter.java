package org.dhbw.mosbach.ai.spritCalc18A.spritCalcRESTService;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * logs all HTTP requests
 */
@Provider
public class RequestLoggerFilter implements ContainerRequestFilter {


    private static final Logger LOG = Logger.getLogger(RequestLoggerFilter.class);

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {

        final String method = context.getMethod();
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();

        LOG.infof("Request %s %s from IP %s", method, path, address);
        LOG.info("params:" + request.params().toString());
    }
}
