package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.inject.Inject;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableCollection;
import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.crud.serializer.Tagable;
import br.eti.clairton.vraptor.crud.serializer.TagableExtractor;

public class ModelCollectionSerializer extends Tagable<Model> implements JsonSerializer<Collection<Model>>, HypermediableCollection<Model>, Serializable {
	private static final long serialVersionUID = 1L;
	private final HypermediableCollectionSerializer<Model> hypermediaSerializer;
	private final JsonSerializer<PaginatedCollection<Model, Meta>> paginatedSerialiazer;
	private final Hypermediable<Model> hypermediable;

	@Deprecated
	public ModelCollectionSerializer() {
		this(null, null, null);
	}

	@Inject
	public ModelCollectionSerializer(final HypermediableRule navigator, final TagableExtractor extractor, final Inflector inflector) {
		super(inflector);
		hypermediable = new HypermediableDefault<>();
		hypermediaSerializer = new HypermediableCollectionSerializer<Model>(navigator, inflector) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<Model> getCollectionType() {
				return ModelCollectionSerializer.this.getCollectionType();
			}

			@Override
			public String getResource() {
				return ModelCollectionSerializer.this.getResource();
			}

			@Override
			public String getOperation() {
				return ModelCollectionSerializer.this.getOperation();
			}

			@Override
			public String getRootTag(final Model src) {
				return ModelCollectionSerializer.this.getRootTag(src);
			}

			@Override
			public String getRootTagCollection(final Collection<Model> collection) {
				return ModelCollectionSerializer.this.getRootTagCollection(collection);
			}
		};
		paginatedSerialiazer = new ModelPaginatedSerializer(hypermediaSerializer, inflector);
	}


	@Override
	public Class<Model> getCollectionType() {
		return Model.class;
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
	public JsonElement serialize(final Collection<Model> src, final Type type, final JsonSerializationContext context) {
		if (PaginatedCollection.class.isInstance(src)) {
			@SuppressWarnings("unchecked")
			final PaginatedCollection<Model, Meta> pCollection = (PaginatedCollection<Model, Meta>) src;
			return paginatedSerialiazer.serialize(pCollection, type, context);
		} else {
			return hypermediaSerializer.serialize(src, type, context);
		}
	}
}