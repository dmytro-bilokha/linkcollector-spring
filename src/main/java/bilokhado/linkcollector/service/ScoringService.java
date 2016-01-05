package bilokhado.linkcollector.service;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

import bilokhado.linkcollector.web.QueryTag;
import bilokhado.linkcollector.entity.ScoringResult;
import bilokhado.linkcollector.entity.WebResult;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * A bean to score the given link.
 * 
 */
@Component
public class ScoringService {
	/**
	 * Logger for errors logging.
	 */
	private static final Logger logger = Logger.getLogger("bilokhado.linkcollector.ejb.ScoringBean");

	/**
	 * Reference to {@code ConfigBean} bean for reading timeout value.
	 */
	@Autowired
	private ConfigService config;

	/**
	 * Connection timeout value
	 */
	private int connectTimeout;

	/**
	 * Gets configuration option to store it.
	 */
	@PostConstruct
	private void init() {
		connectTimeout = Integer.parseInt(config.getConfigValue("ConnectTimeout"));
	}

	/**
	 * Downloads the web page and determines: does the page contain strings from
	 * {@code QueryTag} array. If web page is unavailable, returns zero score
	 * and writes problem to log.
	 * 
	 * @param tags
	 *            the array of {@code QueryTag} with evaluation tags
	 * @param wr
	 *            {@code WebResult} object to score
	 * @return {@code Future<ScoringResult>} object for asynchronous getting the
	 *         result of scoring
	 */
	@Async
	public Future<ScoringResult> determineScore(QueryTag[] tags, WebResult wr) {
		/*
		 * String link = wr.getUrl(); Document doc; try { doc =
		 * Jsoup.connect(link).timeout(connectTimeout).get(); } catch (Exception
		 * e) { logger.log(Level.SEVERE, "Unable to get html from url: " +
		 * link); return new AsyncResult<>(new ScoringResult(wr, 0)); } String
		 * pageText = doc.body().text().toLowerCase(); int score = 0; for
		 * (QueryTag q : tags) { if (pageText.contains(q.getTagText())) score +=
		 * q.getTagWeight(); } return new AsyncResult<>(new ScoringResult(wr,
		 * score));
		 */
		return new AsyncResult<>(new ScoringResult(wr, 0));
	}
}
