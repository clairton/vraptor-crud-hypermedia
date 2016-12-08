package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Nodes;
import br.eti.clairton.jpa.serializer.NodesProgramatic;


public abstract class HypermediableSerializer<T> extends br.eti.clairton.gson.hypermedia.HypermediableSerializer<T> implements HypermediableResourceable<T> {
	private static final long serialVersionUID = 1L;
	private final HypermediableResourceable<T> hypermediable;

	public HypermediableSerializer(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		this(new NodesProgramatic(), navigator, em, inflector);
	}
	
	public HypermediableSerializer(final Nodes nodes, final HypermediableRule navigator, @NotNull final EntityManager em, @NotNull final Inflector inflector) {
		super(nodes, navigator, em, inflector);
		this.hypermediable = new DefaultHypermediable<T>();
	}

	@Override
	public String getResource(final Collection<T> src) {
		return hypermediable.getResource(src);
	}

	@Override
	public String getResource(final T src) {
		return hypermediable.getResource(src);
	}

	@Override
	public String getOperation(final T src) {
		return hypermediable.getOperation(src);
	}
}
