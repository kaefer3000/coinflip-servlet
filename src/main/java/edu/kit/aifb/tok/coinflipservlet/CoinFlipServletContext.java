package edu.kit.aifb.tok.coinflipservlet;

import java.util.EnumSet;
import java.util.logging.Logger;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CoinFlipServletContext implements ServletContextListener {

	final static Logger _log = Logger.getLogger(CoinFlipServletContext.class.getName());

	ServletContext _ctx;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		_ctx = sce.getServletContext();

		// Register Servlet
		ServletRegistration sr = _ctx.addServlet("Publishing the results of the tossing of a coin",
				org.glassfish.jersey.servlet.ServletContainer.class);
		sr.addMapping("/*");
		sr.setInitParameter(org.glassfish.jersey.server.ServerProperties.PROVIDER_PACKAGES,
				this.getClass().getPackage().getName() + ","
						+ org.semanticweb.yars.jaxrs.JerseyAutoDiscoverable.class.getPackage().getName());
		sr.setInitParameter(org.glassfish.jersey.server.ServerProperties.WADL_FEATURE_DISABLE,
				Boolean.TRUE.toString());

		FilterRegistration fr;

		// Take CORS implementations from the containers.
		if (_ctx.getServerInfo().startsWith("jetty")) {
			fr = _ctx.addFilter("cross-origin", "org.eclipse.jetty.servlets.CrossOriginFilter");
			fr.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		} else if (_ctx.getServerInfo().toLowerCase().contains("tomcat")) {
			fr = _ctx.addFilter("cross-origin", "org.apache.catalina.filters.CorsFilter");
			fr.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		} else {
			_log.warning("Please configure CORS for your server.");
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
