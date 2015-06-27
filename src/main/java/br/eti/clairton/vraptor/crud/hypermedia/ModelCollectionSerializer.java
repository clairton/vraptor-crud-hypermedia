package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonSerializer;

public class ModelCollectionSerializer extends HypermediableCollectionSerializer<Model> implements JsonSerializer<Collection<Model>> {

	@Inject
	public ModelCollectionSerializer(final HypermediableRule navigator, @Resource final String resource, @Operation final String operation, final Inflector inflector) {
		super(navigator, resource, operation, inflector);
	}

	@Override
	protected Class<Model> getCollectionType() {
		return Model.class;
	}
}