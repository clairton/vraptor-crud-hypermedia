package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.enterprise.inject.Produces;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.hypermedia.HypermediableRule;
import br.eti.clairton.vraptor.hypermedia.Operation;
import br.eti.clairton.vraptor.hypermedia.Resource;

import com.google.gson.JsonSerializer;

public class HypermediableProducer {
	@Produces
	public JsonSerializer<Collection<Model>> getSerializerCollection(
			final HypermediableRule<Model> navigator,
			final @Operation String operation, final @Resource String resource,
			Inflector inflector) {
		return new ModelCollectionSerializer(navigator, operation, resource,
				inflector);
	}
}