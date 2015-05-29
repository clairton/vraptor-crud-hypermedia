package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;

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
public class HypermediableSerializerTest {
	private @Inject GsonBuilderWrapper builder;
	private Gson gson;

	@Before
	public void setUp() {
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final Pessoa object = new Pessoa();
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		assertEquals("Maria", resultado.get("nome"));
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
	}
}
