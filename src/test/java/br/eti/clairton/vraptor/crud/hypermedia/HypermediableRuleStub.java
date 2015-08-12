package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Link;

@Dependent
public class HypermediableRuleStub implements HypermediableRule {
	final Set<Link> links = new HashSet<Link>() {
		private static final long serialVersionUID = 1L;

		{
			add(new Link("/pessoas/1", "update", "Salvar", "PUT", "application/json"));
		}
	};

	@Override
	public <T> Set<Link> from(final Collection<T> model, final String resource, final String operation) {
		return links;
	}

	@Override
	public <T> Set<Link> from(final T model, final String resource, final String operation) {
		return links;
	}
}