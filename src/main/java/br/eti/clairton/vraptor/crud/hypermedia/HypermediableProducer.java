package br.eti.clairton.vraptor.crud.hypermedia;

import static javax.enterprise.inject.spi.CDI.current;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.enterprise.util.AnnotationLiteral;

import net.vidageek.mirror.dsl.Mirror;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;
import br.eti.clairton.repository.PaginatedCollection;
import br.eti.clairton.vraptor.crud.serializer.Producer;
import br.eti.clairton.vraptor.hypermedia.HypermediableRule;
import br.eti.clairton.vraptor.hypermedia.Operation;
import br.eti.clairton.vraptor.hypermedia.Resource;

import com.google.gson.JsonSerializer;

public class HypermediableProducer extends Producer {
	private final Annotation oQ = new AnnotationLiteral<Operation>() {
		private static final long serialVersionUID = 1L;
	};
	private final Annotation rQ = new AnnotationLiteral<Resource>() {
		private static final long serialVersionUID = 1L;
	};

	@Produces
	public JsonSerializer<Collection<Model>> getSerializerCollection(
			final HypermediableRule navigator,
			final @Operation String operation, final @Resource String resource,
			Inflector inflector) {
		return new ModelCollectionSerializer(navigator, operation, resource,
				inflector);
	}

	@Produces
	public <T, X> JsonSerializer<PaginatedCollection<T, X>> PaginatedCollection(
			final JsonSerializer<Collection<Model>> delegate) {
		return new PaginatedCollectionSerializer<T, X>(delegate);
	}

	@Override
	@Produces
	@Specializes
	public JsonSerializer<Model> serializer(Mirror mirror) {
		final Class<HypermediableRule> t = HypermediableRule.class;
		final HypermediableRule navigator = current().select(t).get();
		final String operation = current().select(String.class, oQ).get();
		final String resource = current().select(String.class, rQ).get();
		return new ModelSerializer(navigator, resource, operation);
	}

}