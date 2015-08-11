package br.eti.clairton.vraptor.crud.hypermedia;

import static org.junit.Assert.assertEquals;
import static br.eti.clairton.vraptor.crud.hypermedia.CurrentResource.getResource;

import org.junit.Test;

public class CurrentMethodProducerTest {

	@Test
	public void testGetResourceOnly() {
		assertEquals("aplicacoes", getResource("/aplicacoes"));
	}

	@Test
	public void testGetResourcePathParam() {
		assertEquals("aplicacoes", getResource("/aplicacoes/123"));
	}

	@Test
	public void testGetResourceQueryParam() {
		assertEquals("aplicacoes", getResource("/aplicacoes?per_page=1"));
	}

	@Test
	public void testGetResourceWithoutFirstSlash() {
		assertEquals("aplicacoes", getResource("aplicacoes?per_page=1"));
	}
}
