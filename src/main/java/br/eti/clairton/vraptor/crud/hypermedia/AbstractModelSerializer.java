package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;

public abstract class AbstractModelSerializer<T extends Model> extends HypermediableSerializer<T> implements Hypermediable<T>{
	private static final long serialVersionUID = 1L;
	private final Inflector inflector;
	
	@Deprecated
	public AbstractModelSerializer() {
		this(null, null, null);
	}

	public AbstractModelSerializer(@NotNull final HypermediableRule navigator, @NotNull final Inflector inflector, @NotNull final EntityManager em) {
		super(navigator, em, inflector);
		this.inflector = inflector;
	}

	@Override
	public String getRootTagCollection(Collection<T> collection) {
		return inflector.pluralize(getResource());
	}
}
