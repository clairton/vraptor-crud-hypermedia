package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.caelum.vraptor.serialization.gson.RegisterStrategy;
import br.com.caelum.vraptor.serialization.gson.RegisterType;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Operation;

@RegisterStrategy(RegisterType.SINGLE)
public class TestModelSerialize implements JsonSerializer<TestModel> {
	private final ModelSerializer delegate;

	@Inject
	public TestModelSerialize(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		delegate = new ModelSerializer(navigator, em, inflector){
			private static final long serialVersionUID = 1L;

			{
				ignore("test", Operation.SERIALIZE);
			}
		};
	}

	@Override
	public JsonElement serialize(final TestModel src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
	
	public ModelSerializer getDelegate() {
		return delegate;
	}
}