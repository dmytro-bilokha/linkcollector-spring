package bilokhado.linkcollector.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A service to read, store, and return on demand configuration options.
 *
 */
@Component
public class ConfigService {

	/**
	 * Path to configuration file.
	 */
	private static final String CONFIG_FILE = "/config";

	/**
	 * Logger for errors logging.
	 */
	private static final Logger logger = Logger.getLogger("bilokhado.linkcollector.service.ConfigService");

	/**
	 * Internal map object to store configuration properties.
	 */
	private final Map<String, String> config = new ConcurrentHashMap<>();

	/**
	 * Reads configuration file and stores options in internal map.
	 */
	@PostConstruct
	private void init() {
		Properties props = new Properties();
		try {
			props.load(ConfigService.class.getResourceAsStream(CONFIG_FILE));
			logger.log(Level.INFO, "Properties file loaded successfully");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Unable to load properties file: " + CONFIG_FILE);
			throw new RuntimeException("Could not load config file \"" + CONFIG_FILE + "\"", ex);
		}
		for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			config.put(name, props.getProperty(name));
		}
	}

	/**
	 * Obtains configuration option's value from the internal map.
	 * 
	 * @param name
	 *            the name of parameter to get
	 * @return options string value
	 * @throws IllegalArgumentException
	 *             if the parameter is not found
	 */
	public String getConfigValue(String name) {
		String result = config.get(name);
		if (result == null) {
			logger.log(Level.SEVERE, "Unable to find property: " + name);
			throw new IllegalArgumentException("Property: \"" + name + "\" not found");
		}
		return result;
	}
}
