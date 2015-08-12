package br.eti.clairton.vraptor.crud.hypermedia;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;


public abstract class HypermediableSerializer<T> extends br.eti.clairton.gson.hypermedia.HypermediableSerializer<T> implements Hypermediable<T> {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<T> hypermediable;

	public HypermediableSerializer(@NotNull final HypermediableRule navigator, @NotNull final EntityManager em, @NotNull final Inflector inflector) {
		super(navigator, em, inflector);
		this.hypermediable = new DefaultHypermediable<T>();
	}

	@Override
	public String getResource() {
		return hypermediable.getResource();
	}

	@Override
	public String getOperation() {
		return hypermediable.getOperation();
	}
}
