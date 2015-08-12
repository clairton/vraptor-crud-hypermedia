package br.eti.clairton.vraptor.crud.hypermedia;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;

import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;

@RunWith(CdiTestRunner.class)
public class HypermediableSerializerIntegrationTest {
	private @Inject GsonBuilderWrapper builder;
	private Gson gson;

	@Before
	public void setUp() {
		gson = builder.create();
	}

	@Test
	public void test() {
		final PaginatedCollection<Pessoa, Meta> object = new PaginatedMetaList<>(asList(new Pessoa()), new Meta(1l, 1l));
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(1, models.size());
		//assertTrue(resultado.containsKey("meta"));
	}
}
