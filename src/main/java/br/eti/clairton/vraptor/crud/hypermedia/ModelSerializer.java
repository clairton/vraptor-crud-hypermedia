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
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Nodes;
import br.eti.clairton.repository.Model;

@Specializes
public class ModelSerializer extends br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements JsonSerializer<Model>, JsonDeserializer<Model>, Hypermediable<Model> {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Model> hypermediable;
	private final HypermediableSerializer<Model> serializer;
	private final Inflector inflector;

	@Deprecated
	public ModelSerializer() {
		this(null, null, null);
	}

	@Inject
	public ModelSerializer(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		super(inflector, em);
		this.inflector = inflector;
		serializer = new HypermediableSerializer<Model>(navigator, em, inflector) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getResource(final Model src) {
				return ModelSerializer.this.getResource(src);
			}

			@Override
			public String getOperation(final Model src) {
				return ModelSerializer.this.getOperation(src);
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
		this.hypermediable = new DefaultHypermediable<>();
	}
	
	@Override
	public Nodes nodes() {
		return serializer.nodes();
	}

	@Override
	public String getResource(final Model src) {
		return hypermediable.getResource(src);
	}

	@Override
	public String getOperation(final Model src) {
		return hypermediable.getOperation(src);
	}

	@Override
	public String getRootTagCollection(final Collection<Model> collection) {
		return inflector.pluralize(getResource(collection));
	}

	@Override
	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return serializer.serialize(src, type, context);
	}

	@Override
	public Model deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
		return serializer.deserialize(json, type, context);
	}
}