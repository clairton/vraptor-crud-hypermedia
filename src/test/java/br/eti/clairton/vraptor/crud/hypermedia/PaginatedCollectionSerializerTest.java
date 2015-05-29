package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;

@RunWith(CdiTestRunner.class)
public class PaginatedCollectionSerializerTest {
	private @Inject JSONSerialization serialization;
	private final String expected = "{\"pessoas\":[{\"nome\":\"Maria\",\"links\":[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}]}],"
			+ "\"links\":[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}],"
			+ "\"meta\":[\"total\":101,\"345\"]}";
	private Meta meta = new Meta(101l, 345l);

	@Test
	public void testSerialize() {
		final PaginatedCollection<Pessoa, Meta> object = new PaginatedMetaList<Pessoa>(
				Arrays.asList(new Pessoa()), meta);
		final Serializer serializer = serialization.from(object);
		serializer.serialize();
		final String json = Producer.response.toString();
		assertEquals(expected, json);
	}

}
