/*
 * © University of Bristol
 */
package org.ilrt.wf.facets.interceptor;

import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author cmcpb
 */
public class PropertyInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    private static final Log logger = LogFactory.getLog(PropertyInterceptor.class);

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Create an instance of our extended propertyplaceholderconfigurer to load host-specific properties
     */
    @Override
    public void afterPropertiesSet() {
        if (properties == null) {
            logger.error("Missing properties file");
        }
    }

    /**
     * This interceptor inserts additional properties into all requests. These variables are mostly used by JSP pages.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("Request is " + request.getRequestURI());
        }

        logger.debug(properties.size());

        Enumeration elements = properties.keys();

        while (elements.hasMoreElements()) {
            String key = (String) elements.nextElement();
            String value = properties.getProperty(key);

            if (key.startsWith("ui."))
            {
                key = key.replace("ui.", "");
                request.setAttribute(key, value);

                if (logger.isDebugEnabled()) {
                    logger.debug("Inserting " + key + ":" + value + " into request object");
                }
            }
        }

        return super.preHandle(request, response, handler);
    }

}
