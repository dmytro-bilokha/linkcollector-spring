package bilokhado.linkcollector.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service to clean database from old data.
 */
@Component
public class OldDataEraseService {

	/**
	 * Logger for logging each invocation of database cleaning.
	 */
	private static final Logger logger = Logger.getLogger("bilokhado.linkcollector.service.OldDataEraseService");

	/**
	 * Entity manager for access database.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Reference to {@code ConfigBean} bean for reading value of hours to keep
	 * data.
	 */
	@Autowired
	private ConfigService config;

	/**
	 * String with configuration parameter (hours to keep data).
	 */
	private String keepQueryHours;

	/**
	 * Gets configuration option to stores it.
	 */
	@PostConstruct
	private void init() {
		keepQueryHours = config.getConfigValue("KeepQueryHours");
	}

	/**
	 * Removes old date from database via named MySQL native query
	 */
	@Scheduled(cron = "30 */5 * * * *")
	@Transactional
	public void removeOutdated() {
		Query query = em.createNamedQuery("SearchQuery.deleteOutdated");
		query.setParameter(1, keepQueryHours).executeUpdate();
		em.clear();
		logger.log(Level.INFO, "Outdated data have been removed from database");
	}

}
