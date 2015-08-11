package br.eti.clairton.vraptor.crud.hypermedia;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Link;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;
import br.eti.clairton.repository.Model;

public class PaginatedCollectionSerializerTest {
	private final Meta meta = new Meta(101l, 345l);
	
	private String tag = "testModel";
	private Gson gson;
	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);
	private final HypermediableRule navigator = new HypermediableRule() {
		private final Set<Link> links = new HashSet<Link>(){
			private static final long serialVersionUID = 1L;

			{
				add(new Link(null, "new", null, null, null));
			}
		};
		@Override
		public <T> Set<Link> from(final T target, final String resource, final String operation) {
			return links;
		}
		
		@Override
		public <T> Set<Link> from(final Collection<T> target, final String resource, final String operation) {
			return links;
		}
	};
		

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeHierarchyAdapter(Model.class, new ModelSerializer(navigator, null, inflector){
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getResource() {
				return tag;
			}
			
			public String getOperation() {
				return tag;
			}
			
		});
		builder.registerTypeHierarchyAdapter(PaginatedCollection.class, new HypermediablePaginatedCollectionSerializer<Model, Meta>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getRootTag(final Model src) {
				return tag;
			}
			
			@Override
			public String getResource() {
				return tag;
			}
			
			public String getOperation() {
				return tag;
			}

			@Override
			public String getRootTagCollection(Collection<Model> collection) {
				return tag;
			}
		});
		gson = builder.create();
	}

	@Test
	public void testWithLink() {
		final PaginatedCollection<TestModel, Meta> object = new PaginatedMetaList<TestModel>(asList(), meta);
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		//assertTrue(resultado.containsKey("links"));
		assertTrue(resultado.containsKey("meta"));
	}

	@Test
	public void testWithoutLink() {
		final PaginatedCollection<TestModel, Meta> object = new PaginatedMetaList<TestModel>(asList(new TestModel()), meta);
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
//		assertFalse(resultado.containsKey("links"));
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("101.0"), meta.get("total"));
		assertEquals(Double.valueOf("345.0"), meta.get("page"));
	}

	@Test
	public void testSerialize() {
		final List<Pessoa> pessoas = asList(new Pessoa());
		final PaginatedCollection<Pessoa, Meta> object = new PaginatedMetaList<Pessoa>(pessoas, meta);
		final String json = gson.toJson(object);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
//		final List<?> links = (List<?>) resultado.get("links");
//		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("testModel");
		assertEquals(1, models.size());
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("101.0"), meta.get("total"));
		assertEquals(Double.valueOf("345.0"), meta.get("page"));
	}
}
