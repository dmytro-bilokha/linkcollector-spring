package bilokhado.linkcollector.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/processing")
public class ProcessingController {

	@RequestMapping(method = GET)
	public String processingPage() {
		return "/processing";
	}
}
