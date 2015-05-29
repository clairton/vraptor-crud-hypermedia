package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Method;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.http.MutableRequest;

@Specializes
public class CurrentResourceOverride extends CurrentResource {

	@Inject
	public CurrentResourceOverride(MutableRequest request,
			ControllerMethod method) {
		super(request, getControllerMethod());
	}

	private static ControllerMethod getControllerMethod() {
		try {
			final Method method = PessoaController.class.getMethod("index");
			return DefaultControllerMethod.instanceFor(PessoaController.class,
					method);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Produces
	@Resource
	public String getResource() {
		return "";
	}

	@Produces
	@Operation
	public String getOperation() {
		return "";
	}
}
