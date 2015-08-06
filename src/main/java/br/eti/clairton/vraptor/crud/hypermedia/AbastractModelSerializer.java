package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.persistence.EntityManager;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Nodes;
import br.eti.clairton.repository.Model;

public abstract class AbastractModelSerializer<T extends Model> extends br.eti.clairton.vraptor.crud.serializer.AbstractModelSerializer<T>{
	private static final long serialVersionUID = 1L;
	private final HypermediableSerializer<T> delegate;

	@Deprecated
	public AbastractModelSerializer() {
		this(null, null, null);
	}

	public AbastractModelSerializer(final HypermediableRule navigator, final Inflector inflector, final EntityManager em) {
		delegate = new HypermediableSerializer<T>(navigator, em, inflector);
	}

	public JsonElement serialize(final T src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}

	public String getRootTag(final T src) {
		return delegate.getRootTag(src);
	}

	public String getRootTagCollection(final Collection<T> collection) {
		return delegate.getRootTagCollection(collection);
	}

	public Nodes nodes() {
		return delegate.nodes();
	}

	public T deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
		return delegate.deserialize(json, type, context);
	}
}
