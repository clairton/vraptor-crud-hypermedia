package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.HypermediableSerializer;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Specializes
public class ModelSerializer extends br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements JsonSerializer<Model> {
	private final HypermediableSerializer<Model> delegate;
	
	
	@Inject
	public ModelSerializer(final HypermediableRule navigator, final @Resource String resource, final @Operation String operation) {
		delegate = new HypermediableSerializer<Model>(navigator, resource, operation){};
	}
	
	@Override
	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}