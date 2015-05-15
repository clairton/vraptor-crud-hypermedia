package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.enterprise.inject.Vetoed;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.vraptor.hypermedia.HypermediableRule;

import com.google.gson.JsonSerializer;

@Vetoed
public class ModelCollectionSerializer extends
		HypermediableCollectionSerializer<Model> implements
		JsonSerializer<Collection<Model>> {

	public ModelCollectionSerializer(HypermediableRule navigator,
			String operation, String resource, Inflector inflector) {
		super(navigator, operation, resource, inflector);
	}

	@Override
	protected Class<Model> getCollectionType() {
		return Model.class;
	}
}