package br.eti.clairton.vraptor.crud.hypermedia;

import static br.eti.clairton.vraptor.crud.hypermedia.VRaptorRunner.navigate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.TransactionManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.test.VRaptorTestResult;
import br.com.caelum.vraptor.test.requestflow.UserFlow;

import com.google.gson.Gson;

@RunWith(VRaptorRunner.class)
public class CrudControllerTest {
	private final Gson gson = new Gson();
	private @Inject EntityManager entityManager;
	private @Inject Connection connection;

	private Long id;
	private TransactionManager tm;

	@Before
	public void init() throws Exception {
		final InitialContext context = new InitialContext();
		final Class<TransactionManager> t = TransactionManager.class;
		final String jndi = "java:/TransactionManager";
		tm = t.cast(context.lookup(jndi));
		tm.begin();
		final String sql = "DELETE FROM aplicacao;";
		connection.createStatement().execute(sql);
		Aplicacao aplicacao = new Aplicacao("Teste");
		aplicacao = new Aplicacao("TesteOutro");
		entityManager.persist(aplicacao);
		aplicacao = new Aplicacao("Testezinho");
		entityManager.persist(aplicacao);
		entityManager.joinTransaction();
		entityManager.flush();
		entityManager.clear();
		tm.commit();
		id = aplicacao.getId();
	}

	@Test
	public void testIndex() {
		final UserFlow flow = navigate().get("/aplicacoes");
		final VRaptorTestResult result = flow.execute();
		assertEquals(200, result.getResponse().getStatus());
		final String response = result.getResponseBody();
		final Map<?, ?> o = gson.fromJson(response, Map.class);
		assertNotNull(o);
		final List<?> aplicacoes = (List<?>) o.get("aplicacoes");
		assertNotNull(aplicacoes);
		assertEquals(2, aplicacoes.size());
		final List<?> links = (List<?>) o.get("links");
		assertEquals(1, links.size());
	}

	@Test
	public void testShow() {
		final UserFlow flow = navigate().get("/aplicacoes/" + id + "/edit");
		final VRaptorTestResult result = flow.execute();
		assertEquals(200, result.getResponse().getStatus());
		final String response = result.getResponseBody();
		final Map<?, ?> o = gson.fromJson(response, Map.class);
		final Map<?, ?> aplicacao = (Map<?, ?>) o.get("aplicacao");
		assertEquals("Testezinho", aplicacao.get("nome"));
		final List<?> links = (List<?>) aplicacao.get("links");
		assertEquals(1, links.size());
		assertEquals(Double.valueOf(id), aplicacao.get("id"));
	}
}
