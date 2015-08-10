package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;

import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;

@Vetoed
public class ModelPaginatedSerializer extends HypermediablePaginatedCollectionSerializer<Model, Meta> implements JsonSerializer<PaginatedCollection<Model, Meta>>, Serializable {
	private static final long serialVersionUID = 1L;

	public ModelPaginatedSerializer(final HypermediableCollectionSerializer<Model> delegate, final Inflector inflector) {
		super(delegate, inflector);
	}
}
