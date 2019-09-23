package virgo47.restapidemo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.MediaTypeExpression;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/** Support for primitive (yet always up-to-date)  REST API index endpoint (HTML and JSON). */
@RestController
public class RestApiIndex {

	private final RequestMappingHandlerMapping handlerMapping;
	private final List<OperationInfo> uiRestInfo;

	public RestApiIndex(RequestMappingHandlerMapping handlerMapping) {
		// Handler mapping already has all the information - even about this class - as it
		// scans type information before controller instances are created. Sleep well.
		this.handlerMapping = handlerMapping;
		uiRestInfo = operationInfoStream()
			// We can filter by class (or not class as here), package or URL patterns to focus
			// on a single API if multiple APIs are implemented by a single application.
			.filter(info -> !info.handler.getBeanType().equals(RestApiIndex.class))
			.collect(Collectors.toList());
	}

	@GetMapping
	public List<OperationJson> index() {
		return uiRestInfo.stream()
			.flatMap(OperationInfo::operationJsonStream)
			.sorted(Comparator.comparing(json -> json.urlPattern))
			.collect(Collectors.toList());
	}

	@GetMapping(produces = "text/html")
	public String indexHtml() {
		StringBuilder html = new StringBuilder("<!DOCTYPE html><html><head>"
			+ "<meta charset='UTF-8'><title>Admin API page</title>"
			+ "<style>body {font-family: sans-serif;} li,h1 {margin: 0.4em;}</style>"
			+ "</head>"
			+ "<body><h1>REST operations</h1>This is NOT Swagger! Click at your own risk!<ul>");
		for (OperationJson operationJson : index()) {
			html.append("<li>")
				.append(Arrays.toString(operationJson.methods))
				.append(" <a href=\"")
				.append(operationJson.urlPattern)
				.append("\">")
				.append(operationJson.urlPattern)
				.append("</a></li>");
		}
		return html.append("</ul></body>")
			.toString();
	}

	// here not necessary as a separate method, but handy for more separate API index pages
	private Stream<OperationInfo> operationInfoStream() {
		return handlerMapping.getHandlerMethods().entrySet().stream()
			.map(entry -> new OperationInfo(entry.getKey(), entry.getValue()));
	}

	private static class OperationInfo {
		final RequestMappingInfo mappingInfo;
		final HandlerMethod handler;

		OperationInfo(RequestMappingInfo mappingInfo, HandlerMethod handler) {
			this.mappingInfo = mappingInfo;
			this.handler = handler;
		}

		Stream<OperationJson> operationJsonStream() {
			return mappingInfo.getPatternsCondition().getPatterns().stream()
				.map(pattern -> new OperationJson(pattern,
					mappingInfo.getMethodsCondition().getMethods(),
					mappingInfo.getConsumesCondition().getConsumableMediaTypes(),
					mappingInfo.getProducesCondition().getExpressions()));
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class OperationJson {
		public final String urlPattern;
		public final String[] methods;
		public final String[] accepts;
		public final String[] produces;

		public OperationJson(String urlPattern,
			Set<RequestMethod> methods,
			Set<MediaType> accepts,
			Set<MediaTypeExpression> produces)
		{
			this.urlPattern = urlPattern;
			this.methods = toStringArray(methods);
			this.accepts = toStringArray(accepts);
			this.produces = toStringArray(produces);
		}

		private String[] toStringArray(Collection<?> collection) {
			return collection.isEmpty()
				? null
				: collection.stream()
					.map(Object::toString)
					.toArray(String[]::new);
		}
	}
}
