package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.com.caelum.vraptor.serialization.gson.RegisterStrategy;
import br.com.caelum.vraptor.serialization.gson.RegisterType;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.hypermedia.HypermediableRule;
import br.eti.clairton.vraptor.hypermedia.Operation;
import br.eti.clairton.vraptor.hypermedia.Resource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@RunWith(CdiTestRunner.class)
public class SerializerTest {
	private @Inject GsonBuilderWrapper builder;
	private Gson gson;

	@Before
	public void setUp() {
		gson = builder.create();
	}

	@Test
	public void test() {
		final TestModel model = new TestModel();
		final String json = gson.toJson(model);
		final Map<?, ?> map = gson.fromJson(json, Map.class);
		assertEquals(model.name, map.get("name"));
		assertTrue(map.containsKey("links"));
	}

	@Produces
	public JsonSerializer<TestModel> getSerializer(
			final HypermediableRule navigator,
			final @Operation String operation, final @Resource String resource) {
		return new TestModelSerialize(navigator, resource, operation);
	}
}

class TestModel extends Model {
	private static final long serialVersionUID = 1L;

	public String name = "vendelina";
}

@RegisterStrategy(RegisterType.SINGLE)
class TestModelSerialize implements JsonSerializer<TestModel> {
	private final ModelSerializer delegate;

	public TestModelSerialize(HypermediableRule navigator,
			@Resource String resource, @Operation String operation) {
		delegate = new ModelSerializer(navigator, resource, operation);
	}

	@Override
	public JsonElement serialize(final TestModel src, final Type type,
			final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}
