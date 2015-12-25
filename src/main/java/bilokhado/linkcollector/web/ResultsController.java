package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/results")
public class ResultsController {

	@RequestMapping(method = GET)
	public String resultsPage(
			@RequestParam(value = "tags", required = false) String tagsString,
			Model model) {
		TagsList tags = new TagsList();
		if (tagsString != null && !tagsString.isEmpty()) {
			try {
				tags.populateFromJson(tagsString);
			} catch (Exception e) {
				// We just ignore exception, because request param is not
				// mandatory
			}
		}
		model.addAttribute("tagslist", tags);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return "/results";
	}

}
