package br.eti.clairton.vraptor.crud.hypermedia;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.http.MutableRequest;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.security.Extractor;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

@Dependent
public class CurrentResource {
	private final MutableRequest request;
	private final ControllerMethod method;
	private final Inflector inflector;
	private final Extractor extractor;

	@Inject
	public CurrentResource(final MutableRequest request,
			final ControllerMethod method, final Inflector inflector,
			final Extractor extractor) {
		this.request = request;
		this.method = method;
		this.inflector = inflector;
		this.extractor = extractor;
	}

	@Produces
	@Resource
	public String getResource() {
		final String resource = inflector.singularize(getResource(request.getRequestedUri()));
		return resource;
	}

	@Produces
	@Operation
	public String getOperation() {
		return extractor.getOperation(method.getMethod());
	}

	public static String getResource(final String uri) {
		final String withouQuery = uri.split("\\?")[0];
		final String[] splitSlash = withouQuery.split("/");
		final String withoutSlash = splitSlash[splitSlash.length >= 2 ? 1 : 0];
		return withoutSlash;
	}
}
