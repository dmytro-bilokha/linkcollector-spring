package bilokhado.linkcollector.appconfig;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * A class to initialize the web component.
 *
 */
public class WebInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Sets root configuration classes.
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class, WebConfig.class, WebFlowConfig.class };
	}

	/**
	 * Sets servlet configuration classes.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	/**
	 * Sets servlet URL mapping.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * Sets servlet filter to force UTF-8 encoding.
	 */
	@Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }
}
