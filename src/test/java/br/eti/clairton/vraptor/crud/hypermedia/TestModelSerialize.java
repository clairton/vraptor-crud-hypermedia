package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import javax.inject.Inject;

import br.com.caelum.vraptor.serialization.gson.RegisterStrategy;
import br.com.caelum.vraptor.serialization.gson.RegisterType;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.jpa.serializer.Mode;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@RegisterStrategy(RegisterType.SINGLE)
public class TestModelSerialize implements JsonSerializer<TestModel> {
	private final ModelSerializer delegate;

	@Inject
	public TestModelSerialize(final HypermediableRule navigator) {
		delegate = new ModelSerializer(navigator);
		delegate.nodes().put("test", Mode.IGNORE);
	}

	@Override
	public JsonElement serialize(final TestModel src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}