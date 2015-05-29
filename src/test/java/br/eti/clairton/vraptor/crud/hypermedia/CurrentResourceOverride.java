package br.eti.clairton.vraptor.crud.hypermedia;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;

import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

public class CurrentResourceOverride extends CurrentResource {

	public CurrentResourceOverride() {
		super(null, null, null, null);
	}

	@Produces
	@Resource
	@Specializes
	public String getResource() {
		return "pessoa";
	}

	@Produces
	@Operation
	@Specializes
	public String getOperation() {
		return "index";
	}
}
