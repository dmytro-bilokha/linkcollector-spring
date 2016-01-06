package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller for search home page.
 *
 */
@Controller
@RequestMapping("/")
public class SearchController {

	/**
	 * Redirects from application root to begin of the search web flow.
	 * 
	 * @return redirection string
	 */
	@RequestMapping(method = GET)
	public String redirectToSearchFlow() {
		return "redirect:/search";
	}

	/**
	 * Creates search form for web flow. If both parameters are not {@code null}
	 * creates form filled with data from parameters. Otherwise creates blank
	 * form.
	 * 
	 * @param query
	 *            search query string
	 * @param tagsString
	 *            string representing tags list
	 * @return search form object
	 */
	public SearchForm getSearchForm(String query, String tagsString) {
		if (query != null && tagsString != null)
			return new SearchForm(query, tagsString);
		return new SearchForm();
	}

}
