package br.eti.clairton.vraptor.crud.hypermedia;

import javax.persistence.EntityManager;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;

public abstract class AbstractModelSerializer<T extends Model> extends HypermediableSerializer<T> implements Hypermediable<T>{
	private static final long serialVersionUID = 1L;

	@Deprecated
	public AbstractModelSerializer() {
		this(null, null, null);
	}

	public AbstractModelSerializer(final HypermediableRule navigator, final Inflector inflector, final EntityManager em) {
		super(navigator, em, inflector);
	}
}
