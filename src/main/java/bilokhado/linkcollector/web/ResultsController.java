package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bilokhado.linkcollector.entity.TagsList;
import bilokhado.linkcollector.service.SearchService;

@Controller
@RequestMapping("/results")
public class ResultsController {

	@Autowired
	SearchService searcher;

	@RequestMapping(method = GET)
	public String resultsPage(@RequestParam(value = "query", required = true) String encodedQuery,
			@RequestParam(value = "tags", required = true) String tagsString, Model model) throws Exception {
		String query = new String(Base64.getUrlDecoder().decode(encodedQuery), StandardCharsets.UTF_8);
		TagsList tags = new TagsList();
		tags.populateFromJson(tagsString);
		tags.normalize();
		model.addAttribute("results", searcher.search(query, tags));
		model.addAttribute("searchQuery", query);
		return "/results";
	}

}
