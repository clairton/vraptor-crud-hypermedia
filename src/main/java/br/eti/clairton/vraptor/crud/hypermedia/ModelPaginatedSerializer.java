package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;

import com.google.gson.JsonSerializer;

public class ModelPaginatedSerializer extends HypermediablePaginatedCollectionSerializer<Model, Meta> implements JsonSerializer<PaginatedCollection<Model, Meta>> {

	@Inject
	public ModelPaginatedSerializer(final JsonSerializer<Collection<Model>> delegate) {
		super(delegate);
	}
}
