package bilokhado.linkcollector.appconfig;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import bilokhado.linkcollector.appconfig.RootConfig.WebPackage;

@Configuration
@ComponentScan(basePackages = { "bilokhado.linkcollector" }, excludeFilters = { @Filter(type = FilterType.CUSTOM, value = WebPackage.class) })
public class RootConfig {

	public static class WebPackage extends RegexPatternTypeFilter {

		public WebPackage() {
			super(Pattern.compile("bilokhado.linkcollector\\.web"));
		}

	}

}
