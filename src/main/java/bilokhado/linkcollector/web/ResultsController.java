package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bilokhado.linkcollector.service.ConfigService;
import bilokhado.linkcollector.service.StoreService;

@Controller
@RequestMapping("/results")
public class ResultsController {

	@Autowired
	ConfigService conf;

	@Autowired
	StoreService storeService;

	@RequestMapping(method = GET)
	public String resultsPage(@RequestParam(value = "query", required = true) String query,
			@RequestParam(value = "tags", required = true) String tagsString, Model model) {
		model.addAttribute("query", new String(Base64.getUrlDecoder().decode(query), StandardCharsets.UTF_8));
		TagsList tags = new TagsList();
		if (tagsString != null && !tagsString.isEmpty()) {
			try {
				tags.populateFromJson(tagsString);
			} catch (Exception e) {
				// We just ignore exception, because request param is not
				// mandatory
			}
			storeService.store(tagsString);
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
