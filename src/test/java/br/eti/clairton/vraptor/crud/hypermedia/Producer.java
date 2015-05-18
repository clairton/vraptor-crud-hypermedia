package br.eti.clairton.vraptor.crud.hypermedia;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Language;
import br.eti.clairton.inflector.Locale;
import br.eti.clairton.repository.AttributeBuilder;
import br.eti.clairton.security.App;
import br.eti.clairton.security.Gate;
import br.eti.clairton.security.GateInMemory;
import br.eti.clairton.security.Lock;
import br.eti.clairton.security.LockInMemory;
import br.eti.clairton.security.Locksmith;
import br.eti.clairton.security.LocksmithInMemory;
import br.eti.clairton.security.Token;
import br.eti.clairton.security.User;

public class Producer {
	public static final String TENANT = "valorQueNãoPodeAparecer";

	private final Mirror mirror = new Mirror();

	private EntityManagerFactory emf;

	private EntityManager em;

	private AttributeBuilder attributeBuilder;

	@PostConstruct
	public void init() {
		emf = Persistence.createEntityManagerFactory("default");
		em = emf.createEntityManager();
		attributeBuilder = new AttributeBuilder(em);
	}

	@Produces
	public EntityManager getEm() {
		return em;
	}

	@Produces
	public Metamodel getMetamodel() {
		return em.getMetamodel();
	}

	@Produces
	public Cache getCache() {
		return Mockito.mock(Cache.class);
	}

	@Produces
	public Mirror getMirror() {
		return mirror;
	}

	@User
	@Produces
	public String getUser(@Token final String token,
			@Default final Locksmith locksmith) {
		return "pedro";
	}

	@App
	@Produces
	public String getApp() {
		return "test";
	}

	@Produces
	public Inflector getForLanguage(final InjectionPoint ip) {
		final String language;
		if (ip.getAnnotated().isAnnotationPresent(Language.class)) {
			language = ip.getAnnotated().getAnnotation(Language.class).value();
		} else {
			language = Locale.pt_BR;
		}
		final Inflector inflector = Inflector.getForLocale(language);
		return inflector;
	}

	@Produces
	public AttributeBuilder getAttributeBuilder() {
		return attributeBuilder;
	}

	@Produces
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		final Class<?> type = injectionPoint.getMember().getDeclaringClass();
		final String klass = type.getName();
		return LogManager.getLogger(klass);
	}

	@Produces
	public Lock getLock() {
		return new LockInMemory();
	}

	@Produces
	public Locksmith getLocksmith(@Default Lock lock) {
		return new LocksmithInMemory(lock);
	}

	@Token
	@Produces
	public String getToken() {
		return "lsdkngdiewGA8UYI42LEHN";
	}

	@Produces
	public Gate getGate() {
		final Map<String, Map<String, List<String>>> roles = new HashMap<String, Map<String, List<String>>>() {
			private static final long serialVersionUID = 1L;

			{
				put("Pass", new HashMap<String, List<String>>() {
					private static final long serialVersionUID = 1L;
					{
						put("aplicacao", Arrays.asList("create", "update", "show", "edit"));
					}
				});
			}
		};
		final Map<String, Map<String, Map<String, List<String>>>> authorizations = new HashMap<String, Map<String, Map<String, List<String>>>>();
		authorizations.put("admin", roles);
		return new GateInMemory();
	}

	@Produces
	public Connection getConnection() {
		try {
			final Context context = new InitialContext();
			final String jndi = "java:/jdbc/datasources/DefaultDS";
			final DataSource ds = (DataSource) context.lookup(jndi);
			return ds.getConnection();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
