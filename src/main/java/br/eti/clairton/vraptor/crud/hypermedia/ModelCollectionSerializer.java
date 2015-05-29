package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.enterprise.inject.Vetoed;

import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;

import com.google.gson.JsonSerializer;

@Vetoed
public class ModelCollectionSerializer extends
		HypermediableCollectionSerializer<Model> implements
		JsonSerializer<Collection<Model>> {

	public ModelCollectionSerializer(HypermediableRule navigator,
			String resource, String operation, Inflector inflector) {
		super(navigator, resource, operation, inflector);
	}

	@Override
	protected Class<Model> getCollectionType() {
		return Model.class;
	}
}