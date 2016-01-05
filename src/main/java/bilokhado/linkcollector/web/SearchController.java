package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SearchController {

	@RequestMapping(method = GET)
	public String redirectToSearchFlow() {
		return "redirect:/search";
	}

	public SearchForm getSearchForm(String query, String tagsString) {
		if (query != null && tagsString != null)
			return new SearchForm(query, tagsString);
		return new SearchForm();
	}

}
