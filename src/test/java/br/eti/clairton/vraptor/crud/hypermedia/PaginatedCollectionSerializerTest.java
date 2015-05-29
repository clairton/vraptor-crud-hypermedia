package br.eti.clairton.vraptor.crud.hypermedia;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;

import com.google.gson.Gson;

@RunWith(CdiTestRunner.class)
public class PaginatedCollectionSerializerTest {
	private final Meta meta = new Meta(101l, 345l);

	private @Inject GsonBuilderWrapper builder;

	private Gson gson;

	@Before
	public void setUp() {
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final PaginatedCollection<TestModel, Meta> object = new PaginatedMetaList<TestModel>(
				asList(new TestModel()), meta);
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("testModeis");
		assertEquals(1, models.size());
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("101.0"), meta.get("total"));
		assertEquals(Double.valueOf("345.0"), meta.get("page"));
	}

	//@Test
	public void testSerializeEmpty() {
		final PaginatedCollection<TestModel, Meta> pessoas = new PaginatedMetaList<TestModel>(
				emptyList(), meta);
		final String json = gson.toJson(pessoas);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("testModeis");
		assertEquals(1, models.size());
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("101.0"), meta.get("total"));
		assertEquals(Double.valueOf("345.0"), meta.get("page"));
	}

}
