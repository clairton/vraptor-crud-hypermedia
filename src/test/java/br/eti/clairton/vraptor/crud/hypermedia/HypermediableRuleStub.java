package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.hypermedia.HypermediableRule;
import br.eti.clairton.vraptor.hypermedia.Link;

@Dependent
public class HypermediableRuleStub implements HypermediableRule<Model> {

	@Override
	public Set<Link> from(final Collection<Model> model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}

	@Override
	public Set<Link> from(final Model model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}
}