package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;

import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.eti.clairton.jpa.serializer.Operation;

@RunWith(CdiTestRunner.class)
public class SerializerTest {
	private @Inject GsonBuilderWrapper builder;
	private Gson gson;

	@Before
	public void setUp() {
		gson = builder.create();
	}

	@Test
	public void test2() {
		assertTrue(new TestModelSerialize(null, null, null).getDelegate().nodes().isIgnore("test", Operation.SERIALIZE));
	}

	@Test
	public void test() {
		final TestModel model = new TestModel();
		final String json = gson.toJson(model);
		final Map<?, ?> map = gson.fromJson(json, Map.class);
		assertEquals(model.name, map.get("name"));
		assertFalse(map.containsKey("test"));
	}
}
