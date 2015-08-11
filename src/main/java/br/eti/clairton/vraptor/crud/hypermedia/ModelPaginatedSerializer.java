package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Type;

import javax.enterprise.inject.Vetoed;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;

@Vetoed
public class ModelPaginatedSerializer extends HypermediablePaginatedCollectionSerializer<Model, Meta> implements JsonSerializer<PaginatedCollection<Model, Meta>>, Serializable {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Model> hypermediable;

	public ModelPaginatedSerializer(final HypermediableCollectionSerializer<Model> delegate, final Inflector inflector) {
		super(delegate);
		hypermediable = new DefaultHypermediable<Model>();		
	}
	
	@Override
	public JsonElement serialize(PaginatedCollection<Model, Meta> src, Type type, JsonSerializationContext context) {
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
