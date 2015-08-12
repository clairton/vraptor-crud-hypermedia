package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.annotation.Priority;
import javax.inject.Inject;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.crud.serializer.Tagable;

/**
 * Prioridade baixa ser o ultimo a ser carregado.
 */
@Priority(1000)
public class ModelPaginatedSerializer extends HypermediablePaginatedCollectionSerializer<Model, Meta> implements JsonSerializer<PaginatedCollection<Model, Meta>>, Serializable {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Model> hypermediable;
	private final Tagable<Model> tagable;

	@Deprecated
	public ModelPaginatedSerializer() {
		this(null);
	}
	
	@Inject
	public ModelPaginatedSerializer(final Inflector inflector) {
		hypermediable = new DefaultHypermediable<Model>();	
		this.tagable = new Tagable<Model>(inflector){
			private static final long serialVersionUID = 1L;

			@Override
			public String getRootTag(final Model src) {
				return ModelPaginatedSerializer.this.getRootTag(src);
			}

			@Override
			public String getResource() {
				return  ModelPaginatedSerializer.this.getResource();
			}
		};	
	}
	
	@Override
	public JsonElement serialize(final PaginatedCollection<Model, Meta> src, final Type type, final JsonSerializationContext context) {
		return super.serialize(src, type, context);
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
	public String getRootTagCollection(final Collection<Model> collection) {
		return tagable.getRootTagCollection(collection);
	}
}
