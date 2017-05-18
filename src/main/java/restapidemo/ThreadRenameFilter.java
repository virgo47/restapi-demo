package restapidemo;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ThreadRenameFilter implements Filter {

	public static final String REQ_ID_ATTRIBUTE = "requestId";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
		String requestId = UUID.randomUUID().toString();
		Thread.currentThread().setName(requestId + ": " + uri.getPath());
		request.setAttribute(REQ_ID_ATTRIBUTE, requestId);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
