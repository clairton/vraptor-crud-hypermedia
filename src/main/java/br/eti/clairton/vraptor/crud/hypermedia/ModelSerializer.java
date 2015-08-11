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

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Tagable;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;

@Specializes
public class ModelSerializer extends br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements JsonSerializer<Model>, JsonDeserializer<Model>, Hypermediable<Model> {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Model> hypermediable;
	private final HypermediableSerializer<Model> serializer;
	private final Tagable<Model> tagable;

	@Deprecated
	public ModelSerializer() {
		this(null, null, null);
	}

	@Inject
	public ModelSerializer(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		super(inflector, em);
		serializer = new HypermediableSerializer<Model>(navigator, em, inflector) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getResource() {
				return ModelSerializer.this.getResource();
			}

			@Override
			public String getOperation() {
				return ModelSerializer.this.getOperation();
			}
			
			@Override
			public String getRootTag(final Model src) {
				return ModelSerializer.this.getRootTag(src);
			}
			
			@Override
			public String getRootTagCollection(final Collection<Model> collection) {
				return ModelSerializer.this.getRootTagCollection(collection);
			}

		};
		tagable = new Tagable<>(inflector, this);
		this.hypermediable = new DefaultHypermediable<>();
	}

	@Override
	public String getResource() {
		return hypermediable.getResource();
	}

	@Override
	public String getOperation() {
		return hypermediable.getOperation();
	}

	@Override
	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return serializer.serialize(src, type, context);
	}

	@Override
	public Model deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
		return serializer.deserialize(json, type, context);
	}
	
	@Override
	public String getRootTagCollection(final Collection<Model> collection) {
		return tagable.getRootTagCollection(collection);
	}
	
	@Override
	public String getRootTag(final Model src) {
		return tagable.getRootTag(src);
	}
}