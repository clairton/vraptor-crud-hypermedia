package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Link;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;

public class HypermediableSerializerTest {
	private Gson gson;
	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);
	private final HypermediableRule navigator = new HypermediableRule() {
		private final Set<Link> links = new HashSet<Link>() {
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
	private final EntityManager em = Mockito.mock(EntityManager.class);

	@Before
	public void setUp() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeHierarchyAdapter(Pessoa.class, new ModelSerializer(navigator, em, inflector) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getResource() {
				return "pessoa";
			}
			
			@Override
			public String getOperation() {
				return "tanto faz";
			}
		});
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
