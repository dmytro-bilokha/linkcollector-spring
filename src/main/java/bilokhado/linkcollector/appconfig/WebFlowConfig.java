package bilokhado.linkcollector.appconfig;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;

/**
 * A class to configure Spring Web Flow.
 */
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

	/**
	 * Reference to main web configuration object.
	 */
	@Autowired
	private WebConfig webMvcConfig;

	/**
	 * Creates and configures flow registry.
	 * 
	 * @return flow registry object
	 */
	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices()).setBasePath("/WEB-INF")
				.addFlowLocationPattern("/**/*-flow.xml").build();
	}

	/**
	 * Creates flow executor object.
	 * 
	 * @return flow executor object
	 */
	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry()).build();
	}

	/**
	 * Creates flow builder services.
	 * 
	 * @return flow builder services object
	 */
	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder().setViewFactoryCreator(mvcViewFactoryCreator()).setValidator(validator())
				.setDevelopmentMode(true).build();
	}

	/**
	 * Creates MVC factory creator and sets view resolver.
	 * 
	 * @return factory creator object
	 */
	@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator() {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setViewResolvers(Arrays.<ViewResolver> asList(this.webMvcConfig.tilesViewResolver()));
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}

	/**
	 * Creates validator factory to enable validation in project.
	 * 
	 * @return validator factory bean
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

}
