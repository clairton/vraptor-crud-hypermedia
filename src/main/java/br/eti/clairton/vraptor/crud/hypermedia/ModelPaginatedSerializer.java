package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;

public class ModelPaginatedSerializer extends HypermediablePaginatedCollectionSerializer<Model, Meta> implements JsonSerializer<PaginatedCollection<Model, Meta>>, Serializable {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Model> hypermediable;

	public ModelPaginatedSerializer() {
		hypermediable = new DefaultHypermediable<Model>();		
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
}
