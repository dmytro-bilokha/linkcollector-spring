package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class SearchController {

	@RequestMapping(method = GET)
	public String redirectToSearchFlow() {
		return "redirect:/search";
	}

	public SearchForm getSearchForm(String tagsString) {
		System.out.println("Need form: " + tagsString);
		return new SearchForm(tagsString);
	}

}
