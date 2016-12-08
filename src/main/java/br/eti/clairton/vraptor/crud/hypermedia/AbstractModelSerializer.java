package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.persistence.EntityManager;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Nodes;
import br.eti.clairton.jpa.serializer.NodesProgramatic;
import br.eti.clairton.repository.Model;

public abstract class AbstractModelSerializer<T extends Model> extends HypermediableSerializer<T> implements Hypermediable<T>{
	private static final long serialVersionUID = 1L;
	private final Inflector inflector;
	
	@Deprecated
	public AbstractModelSerializer() {
		this(null, null, null);
	}

	public AbstractModelSerializer(final Nodes nodes,final HypermediableRule navigator, final Inflector inflector, final EntityManager em) {
		super(nodes, navigator, em, inflector);
		this.inflector = inflector;
	}
	
	public AbstractModelSerializer(final HypermediableRule navigator, final Inflector inflector, final EntityManager em) {
		this(new NodesProgramatic(), navigator, inflector, em);
	}

	@Override
	public String getRootTagCollection(final Collection<T> src) {
		return inflector.pluralize(getResource(src));
	}
}
