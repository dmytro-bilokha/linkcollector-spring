package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bilokhado.linkcollector.service.ConfigService;

@Controller
@RequestMapping("/results")
public class ResultsController {

	@Autowired
	ConfigService conf;
	
	@RequestMapping(method = GET)
	public String resultsPage(
			@RequestParam(value = "tags", required = true) String tagsString,
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
		System.out.println(conf.getConfigValue("AzureKey"));
		return "/results";
	}

}
