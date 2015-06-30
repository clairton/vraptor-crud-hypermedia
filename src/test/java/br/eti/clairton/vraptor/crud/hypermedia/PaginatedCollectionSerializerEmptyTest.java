package br.eti.clairton.vraptor.crud.hypermedia;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletResponse;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.http.VRaptorRequest;
import br.com.caelum.vraptor.http.VRaptorResponse;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.com.caelum.vraptor.util.test.MockHttpServletResponse;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;

import com.google.gson.Gson;

@RunWith(CdiTestRunner.class)
@Priority(Interceptor.Priority.LIBRARY_BEFORE + 1)
@RequestScoped
public class PaginatedCollectionSerializerEmptyTest {
	private final Meta meta = new Meta(101l, 345l);

	public static HttpServletResponse response;

	private @Inject GsonBuilderWrapper builder;

	private Gson gson;

	@Before
	public void init() throws Exception {
		final File file = new File("target/test" + new Date().getTime());
		final OutputStream outputStream = new FileOutputStream(file);
		final PrintWriter writer = new PrintWriter(outputStream);
		response = new MockHttpServletResponse() {

			@Override
			public java.io.PrintWriter getWriter() {
				return writer;
			}

			@Override
			public String toString() {
				try {
					return new String(Files.readAllBytes(file.toPath()));
				} catch (IOException e) {
					return super.toString();
				}
			}
		};
		gson = builder.create();
	}

	private @Inject JSONSerialization serilization;

	@Test
	public void testSerialize() {
		final PaginatedCollection<Pessoa, Meta> object = new PaginatedMetaList<Pessoa>(asList(), meta);
		final Serializer serializer = serilization.from(object, "pessoas");
		serializer.serialize();
		final String json = response.toString();
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(0, models.size());
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("101.0"), meta.get("total"));
		assertEquals(Double.valueOf("345.0"), meta.get("page"));
	}

	@javax.enterprise.inject.Produces
	@Alternative
	@RequestScoped
	public MutableResponse getResponse() {
		return new VRaptorResponse(response);
	}

	@javax.enterprise.inject.Produces
	@Alternative
	@RequestScoped
	public MutableRequest getRequest() {
		return new VRaptorRequest(new MockHttpServletRequest());
	}
}
