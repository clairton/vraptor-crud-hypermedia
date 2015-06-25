package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import javax.enterprise.inject.Vetoed;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.HypermediableSerializer;
import br.eti.clairton.jpa.serializer.JpaSerializer;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Vetoed
public class ModelSerializer implements JsonSerializer<Model> {
	private final HypermediableSerializer<Model> delegate;
	private final JpaSerializer<Model> serializer;

	public ModelSerializer(final HypermediableRule navigator, final @Resource String resource, final @Operation String operation) {
		serializer = new JpaSerializer<Model>();
		delegate = new HypermediableSerializer<Model>(navigator, resource,
				operation, serializer) {
		};
	}

	public void addIgnoredField(final String field) {
		serializer.addIgnoredField(field);
	}

	@Override
	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}