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
import br.eti.clairton.paginated.collection.PaginatedMetaList;
import br.eti.clairton.repository.Model;

/**
 * Prioridade baixa ser o ultimo a ser carregado.
 */
@Priority(1000)
public class ModelPaginatedSerializer extends HypermediablePaginatedCollectionSerializer<Model, Meta> implements JsonSerializer<PaginatedCollection<Model, Meta>>, Serializable {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<PaginatedCollection<Model, Meta>> hypermediable;
	private final Inflector inflector;

	@Deprecated
	public ModelPaginatedSerializer() {
		this(null);
	}
	
	@Inject
	public ModelPaginatedSerializer(final Inflector inflector) {
		hypermediable = new DefaultHypermediable<>();	
		this.inflector = inflector;	
	}
	
	@Override
	public JsonElement serialize(final PaginatedCollection<Model, Meta> src, final Type type, final JsonSerializationContext context) {
		return super.serialize(src, type, context);
	}

	@Override
	public String getResource(final PaginatedCollection<Model, Meta> src) {
		return hypermediable.getResource(src);
	}

	@Override
	public String getOperation(final PaginatedCollection<Model, Meta> src) {
		return hypermediable.getOperation(src);
	}

	@Override
	public String getRootTagCollection(Collection<Model> collection) {
		return inflector.pluralize(getResource(new PaginatedMetaList<>(collection, null)));
	}
}
