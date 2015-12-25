package bilokhado.linkcollector.appconfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class, WebConfig.class, WebFlowConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
//		return new Class<?>[] { WebConfig.class, WebFlowConfig.class };
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
