package br.eti.clairton.vraptor.crud.hypermedia;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.http.MutableRequest;

@Dependent
public class CurrentResource {
	private final MutableRequest request;
	private final ControllerMethod method;

	@Inject
	public CurrentResource(final MutableRequest request,
			final ControllerMethod method) {
		this.request = request;
		this.method = method;
	}

	@Produces
	@Resource
	public String getResource() {
		return getResource(request.getRequestedUri());
	}

	@Produces
	@Operation
	public String getOperation() {
		return getOperation(method);
	}

	private String getOperation(ControllerMethod method) {
		return method.getMethod().getName();
	}

	public static String getResource(final String uri) {
		final String withouQuery = uri.split("\\?")[0];
		final String[] splitSlash = withouQuery.split("/");
		final String withoutSlash = splitSlash[splitSlash.length >= 2 ? 1 : 0];
		return withoutSlash;
	}
}
