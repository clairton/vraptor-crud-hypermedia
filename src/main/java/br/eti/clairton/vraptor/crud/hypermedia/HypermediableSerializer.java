package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;


public abstract class HypermediableSerializer<T> extends br.eti.clairton.gson.hypermedia.HypermediableSerializer<T> implements HypermediableResourceable<T> {
	private static final long serialVersionUID = 1L;
	private final HypermediableResourceable<T> hypermediable;

	public HypermediableSerializer(@NotNull final HypermediableRule navigator, @NotNull final EntityManager em, @NotNull final Inflector inflector) {
		super(navigator, em, inflector);
		this.hypermediable = new DefaultHypermediable<T>();
	}

	@Override
	public String getResource(Collection<T> src) {
		return hypermediable.getResource(src);
	}

	@Override
	public String getResource(T src) {
		return hypermediable.getResource(src);
	}

	@Override
	public String getOperation(T src) {
		return hypermediable.getOperation(src);
	}
}
