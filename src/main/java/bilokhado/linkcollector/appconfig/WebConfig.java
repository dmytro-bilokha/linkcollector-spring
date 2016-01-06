package bilokhado.linkcollector.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

/**
 * 
 * The main class for web properties configuration.
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("bilokhado.linkcollector.web")
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * Reference to web flow configuration object.
	 */
	@Autowired
	private WebFlowConfig webFlowConfig;

	/**
	 * Adds resource handler with needed location.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
	}

	/**
	 * Sets view names for simple, almost static pages to avoid trivial
	 * controllers implementation.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/processing").setViewName("/processing");
		registry.addViewController("/about").setViewName("/about");
		registry.addViewController("/help").setViewName("/help");
		registry.addViewController("/contact").setViewName("/contact");
	}

	/**
	 * Creates flow handler mapping and configure it.
	 * 
	 * @return flow handler mapping object
	 */
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
		handlerMapping.setOrder(-1);
		handlerMapping.setFlowRegistry(this.webFlowConfig.flowRegistry());
		return handlerMapping;
	}

	/**
	 * Creates Thymeleaf view resolver and sets UTF-8 encoding.
	 * 
	 * @param templateEngine
	 *            template engine to set in view resolver
	 * @return view resolver object
	 */
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}

	/**
	 * Creates web flow handler adapter and configures it.
	 * 
	 * @return web flow handler adapter
	 */
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(this.webFlowConfig.flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}

	/**
	 * Creates and configures ajax Thymeleaf view resolver.
	 * 
	 * @return ajax Thymeleaf view resolver object
	 */
	@Bean
	public AjaxThymeleafViewResolver tilesViewResolver() {
		AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setContentType("text/html;charset=UTF-8");
		return viewResolver;
	}

	/**
	 * Creates and configures Spring template engine.
	 * 
	 * @return Spring template engine object
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	/**
	 * Creates and configures template resolver.
	 * 
	 * @return template resolver object
	 */
	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * Enables default servlet handling
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Creates and configures error messages source.
	 * 
	 * @return resource message source object
	 */
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("messages");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}

}
