package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import br.com.caelum.vraptor.serialization.gson.RegisterStrategy;
import br.com.caelum.vraptor.serialization.gson.RegisterType;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@RegisterStrategy(RegisterType.SINGLE)
class TestModelSerialize implements JsonSerializer<TestModel> {
	private final ModelSerializer delegate;

	public TestModelSerialize(final HypermediableRule navigator,
			final @Resource String resource, final @Operation String operation) {
		delegate = new ModelSerializer(navigator, resource, operation);
	}

	@Override
	public JsonElement serialize(final TestModel src, final Type type,
			final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}