package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ModelPaginatedSerializer implements JsonSerializer<PaginatedCollection<Model, Meta>> {
	private final HypermediablePaginatedCollectionSerializer<Model, Meta> delegate;

	@Deprecated
	public ModelPaginatedSerializer() {
		this(null);
	}

	@Inject
	public ModelPaginatedSerializer(final JsonSerializer<Collection<Model>> delegate) {
		this.delegate = new HypermediablePaginatedCollectionSerializer<Model, Meta>(delegate);
	}

	@Override
	public JsonElement serialize(final PaginatedCollection<Model, Meta> src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}
