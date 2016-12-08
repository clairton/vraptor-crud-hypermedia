package br.eti.clairton.vraptor.crud.hypermedia;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TenantValue implements br.eti.clairton.repository.tenant.Value<String> {

	@Override
	public String get() {
		return Producer.TENANT;
	}

}
