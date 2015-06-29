package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;
import java.util.Map;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.jpa.serializer.Mode;
import br.eti.clairton.repository.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Specializes
public class ModelSerializer extends br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements JsonSerializer<Model> {
	private final HypermediableSerializer<Model> delegate;


	@Inject
	public ModelSerializer(final HypermediableRule navigator) {
		delegate = new HypermediableSerializer<Model>(navigator);
	}

	public ModelSerializer(final HypermediableSerializer<Model> delegate) {
		this.delegate = delegate;
	}

	@Override
	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}

	@Override
	public Map<String, Mode> nodes() {
		return delegate.nodes();
	}
}