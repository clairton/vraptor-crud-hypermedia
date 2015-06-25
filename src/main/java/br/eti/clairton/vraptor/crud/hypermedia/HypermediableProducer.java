package br.eti.clairton.vraptor.crud.hypermedia;

import static javax.enterprise.inject.spi.CDI.current;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;

import net.vidageek.mirror.dsl.Mirror;
import br.eti.clairton.gson.hypermedia.HypermediablePaginatedCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonSerializer;

public class HypermediableProducer extends br.eti.clairton.vraptor.crud.serializer.Producer {
	private final Resource rQ = new Resource() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return Resource.class;
		}

		@Override
		public String value() {
			return "";
		}
	};
	private final Operation oQ = new Operation() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return Operation.class;
		}

		@Override
		public String value() {
			return "";
		}
	};

	@Produces
	public JsonSerializer<Collection<Model>> getSerializerCollection(
			final HypermediableRule navigator,
			final @Operation String operation, final @Resource String resource,
			final Inflector inflector) {
		return new ModelCollectionSerializer(navigator, operation, resource,
				inflector);
	}

	@Produces
	public JsonSerializer<PaginatedCollection<Model, Meta>> PaginatedCollection(
			final JsonSerializer<Collection<Model>> delegate) {
		return new HypermediablePaginatedCollectionSerializer<Model, Meta>(
				delegate);
	}

	@Produces
	@Override
	@Specializes
	public JsonSerializer<Model> serializer(Mirror mirror) {
		final Class<HypermediableRule> t = HypermediableRule.class;
		final HypermediableRule navigator = current().select(t).get();
		final String resource = current().select(String.class, rQ).get();
		final String operation = current().select(String.class, oQ).get();
		return new ModelSerializer(navigator, resource, operation);
	}
}