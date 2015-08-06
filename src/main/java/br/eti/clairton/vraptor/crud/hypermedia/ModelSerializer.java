package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Nodes;
import br.eti.clairton.repository.Model;

@Specializes
public class ModelSerializer extends br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements JsonSerializer<Model>, JsonDeserializer<Model> {
	private static final long serialVersionUID = 1L;
	private final AbastractModelSerializer<Model> delegate;
	
	@Deprecated
	public ModelSerializer() {
		this(null, null, null);
	}

	@Inject
	public ModelSerializer(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		delegate = new AbastractModelSerializer<Model>(navigator, inflector, em){
			private static final long serialVersionUID = 1L;
		};
	}

	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}

	public String getRootTag(final Model src) {
		return delegate.getRootTag(src);
	}

	public String getRootTagCollection(final Collection<Model> collection) {
		return delegate.getRootTagCollection(collection);
	}

	public Nodes nodes() {
		return delegate.nodes();
	}

	public Model deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
		return delegate.deserialize(json, type, context);
	}	
}