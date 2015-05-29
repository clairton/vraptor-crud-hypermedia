package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.enterprise.inject.Produces;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.PaginatedCollectionSerializer;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;

import com.google.gson.JsonSerializer;

public class HypermediableProducer {
	@Produces
	public JsonSerializer<Collection<Model>> getSerializerCollection(
			final HypermediableRule navigator,
			final @Operation String operation, final @Resource String resource,
			Inflector inflector) {
		return new ModelCollectionSerializer(navigator, operation, resource,
				inflector);
	}

	@Produces
	public JsonSerializer<PaginatedCollection<Model, Meta>> PaginatedCollection(
			final JsonSerializer<Collection<Model>> delegate) {
		return new PaginatedCollectionSerializer<Model, Meta>(delegate);
	}

	@Produces
	public JsonSerializer<Model> serializer(final HypermediableRule navigator,
			final @Resource String resource, final @Operation String operation) {
		return new ModelSerializer(navigator, resource, operation);
	}
}