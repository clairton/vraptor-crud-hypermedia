package br.eti.clairton.vraptor.crud.hypermedia;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;

import com.google.gson.Gson;

@RunWith(CdiTestRunner.class)
public class HypermediableCollectionSerializerTest {
	private @Inject GsonBuilderWrapper builder;
	private Gson gson;

	@Before
	public void setUp() {
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final List<TestModel> object = asList(new TestModel());
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("testModeis");
		assertEquals(1, models.size());
	}

	// @Test
	public void testSerializeEmpty() {
		final List<Pessoa> pessoas = asList();
		final String json = gson.toJson(pessoas);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(1, models.size());
	}

}
