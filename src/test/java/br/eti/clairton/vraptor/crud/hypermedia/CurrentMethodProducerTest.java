package br.eti.clairton.vraptor.crud.hypermedia;

import static br.eti.clairton.vraptor.crud.hypermedia.CurrentResource.getResource;
import static org.junit.Assert.assertEquals;

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

	// @Test
	// public void testGetResourceNested() {
	// assertEquals("aplicacao", getResource("/another/aplicacoes", inflector));
	// }
	//
	// @Test
	// public void testGetResourceNestedPathParam() {
	// assertEquals("aplicacao",
	// getResource("/another/aplicacoes/123", inflector));
	// }
	//
	// @Test
	// public void testGetResourceNestedPathParamAndQueryParam() {
	// assertEquals(
	// "aplicacao",
	// getResource("/another/aplicacoes/123?page=1&per_page=10",
	// inflector));
	// }
}
