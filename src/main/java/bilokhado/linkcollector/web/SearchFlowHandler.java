package bilokhado.linkcollector.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

public class SearchFlowHandler extends AbstractFlowHandler {

	private static final String DEFAULT_URL = "/processing";

	@Override
	public String handleExecutionOutcome(FlowExecutionOutcome outcome,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> flowout = outcome.getOutput().asMap();
		TagsList tags = (TagsList) flowout.get("tags");
		String tagstr = (String) flowout.get("tagstr");
		return DEFAULT_URL + "?tags=" + tagstr;
	}

	@Override
	public String handleException(FlowException e, HttpServletRequest request,
			HttpServletResponse response) {
		if (e instanceof NoSuchFlowExecutionException) {
			return "/";
		} else {
			throw e;
		}
	}

}
