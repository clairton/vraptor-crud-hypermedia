package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.environment.DefaultEnvironment;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.environment.EnvironmentType;
import br.com.caelum.vraptor.interceptor.DefaultTypeNameExtractor;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.proxy.JavassistProxifier;
import br.com.caelum.vraptor.serialization.Serializee;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilderImpl;
import br.com.caelum.vraptor.util.test.MockHttpServletResponse;
import br.com.caelum.vraptor.util.test.MockInstanceImpl;
import br.com.caelum.vraptor.util.test.MockSerializationResult;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Link;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;
import br.eti.clairton.jpa.serializer.Tagable;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;
import br.eti.clairton.repository.Model;
import br.eti.clairton.repository.Repository;
import br.eti.clairton.repository.vraptor.Page;
import br.eti.clairton.repository.vraptor.QueryParser;
import br.eti.clairton.vraptor.crud.GsonBuilderWrapper;
import br.eti.clairton.vraptor.crud.GsonJSONSerialization;
import br.eti.clairton.vraptor.crud.controller.CrudController;
import br.eti.clairton.vraptor.crud.model.Aplicacao;
import br.eti.clairton.vraptor.crud.serializer.DefaultTagableExtrator;
import br.eti.clairton.vraptor.crud.serializer.TagableExtractor;
import net.vidageek.mirror.dsl.Mirror;

public class ModelCollectionSerializerTest {

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
	
	final TagableExtractor tagableExtractor = new DefaultTagableExtrator(new MockInstanceImpl<>(new ArrayList<Tagable<?>>())){
		@Override
		public String extract(final Object object) {
			if(Collection.class.isInstance(object)){
				return inflector.pluralize(tag);
			}
			return tag;
		}
	};

	private final JsonSerializer<Model> serializer = new br.eti.clairton.vraptor.crud.hypermedia.ModelSerializer(navigator, Mockito.mock(EntityManager.class), inflector){
		private static final long serialVersionUID = 1L;

		@Override
		public String getRootTag(final Model src) {
			return tag;
		}
		
		@Override
		public String getOperation(final Model src) {
			return tag;
		};
		
		@Override
		public String getResource(final Model src) {
			return tag;
		};
	};
	
	private final JsonSerializer<Collection<Model>> collectionSerializer = new ModelCollectionSerializer(navigator, tagableExtractor, inflector){
		private static final long serialVersionUID = 1L;


		@Override
		public String getRootTag(final Model src) {
			return tag;
		}
		
		@Override
		public String getResource(@SuppressWarnings("rawtypes") final Collection src) {
			return tag;
		}
		
		@Override
		public String getOperation(final Collection<Model> src) {
			return tag;
		};
	};

	private final JsonSerializer<PaginatedCollection<Model, Meta>> paginatedSerializer = new ModelPaginatedSerializer(inflector) {
		private static final long serialVersionUID = 1L;

		@Override
		public String getRootTag(final Model src) {
			return tag;
		}
		
		@Override
		public String getResource(final PaginatedCollection<Model, Meta> src) {
			return tag;
		}
		
		public String getOperation(final PaginatedCollection<Model, Meta> src) {
			return tag;
		};
	};
	private final Instance<JsonSerializer<?>> jsonSerializers = new MockInstanceImpl<>(Arrays.asList(collectionSerializer, serializer, paginatedSerializer));
	private final Instance<JsonDeserializer<?>> jsonDeserializers = new MockInstanceImpl<>(new ArrayList<JsonDeserializer<?>>());
	
	private String nome = "Nome da Aplicação Número: " +  new Date().getTime();
	private Repository repository = new Repository(null, null, null, null){
		private static final long serialVersionUID = 1L;
		private final Aplicacao aplicacao = new Aplicacao(nome);
		private final PaginatedCollection<Model, Meta> collection = new PaginatedMetaList<>(Arrays.asList(aplicacao), new Meta(1l, 100l));
		
		@SuppressWarnings("unchecked")
		public <T extends Model, Y> T byId(@NotNull final Class<T> klass, @NotNull final Y id) throws NoResultException {
			return (T) aplicacao;
		}
		
		@SuppressWarnings("unchecked")
		public <T extends Model> br.eti.clairton.paginated.collection.PaginatedCollection<T,Meta> collection(Integer page, Integer perPage) {
			return (PaginatedCollection<T, Meta>) collection;
		};
		
		public <T extends Model> Repository from(java.lang.Class<T> type) {
			return this;
		};
		
		public Repository distinct() {
			return this;
		};
	};
	
	private CrudController<Aplicacao> controller;

	private MockSerializationResult result;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;
	
	private String tag = "xpto";

	private Long id = 1001l;

	@Before
	public void init() throws Exception{
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		final GsonBuilderWrapper builder = new GsonBuilderWrapper(jsonSerializers,  jsonDeserializers, new Serializee()){
			
			protected Class<?> getAdapterType(final Object adapter) {
				if(adapter.equals(paginatedSerializer)){
					return PaginatedCollection.class;
				}else if(adapter.equals(collectionSerializer)){
					return Collection.class;
				}else if(adapter.equals(serializer)){
					return Model.class;
				}else{
					throw new RuntimeException();
				}
			}
		};
		final Environment environment = new DefaultEnvironment(EnvironmentType.TEST);
		final TypeNameExtractor extractor = new DefaultTypeNameExtractor();
		final GsonJSONSerialization serialization = new HypermediableGsonJSONSerialization(response, extractor, builder, environment, tagableExtractor);
		result = new MockSerializationResult(new JavassistProxifier(), XStreamBuilderImpl.cleanInstance(), builder, environment){
			
			@Override
			public <T extends View> T use(final Class<T> view) {
				return view.cast(serialization);
			}
		};
		new Mirror().on(result).set().field("response").withValue(response);
		controller = new AplicacaoController(repository, result, inflector, request, Mockito.mock(QueryParser.class)){
			@Override
			protected Page paginate() {
				return new Page(1, 100);
			}
			
			@Override
			protected void serialize(PaginatedCollection<Aplicacao, Meta> collection) {
				super.serialize(collection);
			}
		};
	}
	
	@Test
	public void testSingle() throws Exception {
		controller.edit(id);
		assertEquals("{\"xpto\":{\"nome\":\""+nome+"\",\"recursos\":[],\"links\":[{\"rel\":\"new\"}]}}", result.serializedResult());
	}
	
	@Test
	public void testCollection() throws Exception {
		controller.index();
		assertEquals("{\"xptos\":[{\"nome\":\""+nome+"\",\"recursos\":[],\"links\":[{\"rel\":\"new\"}]}],\"links\":[{\"rel\":\"new\"}],\"meta\":{\"total\":1,\"page\":100}}", result.serializedResult());
	}
}
