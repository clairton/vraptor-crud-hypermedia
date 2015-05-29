package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Link;

@Dependent
public class HypermediableRuleStub implements HypermediableRule {

	@Override
	public <T> Set<Link> from(final Collection<T> model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}

	@Override
	public <T> Set<Link> from(final T model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}
}