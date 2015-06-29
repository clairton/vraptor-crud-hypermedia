package br.eti.clairton.vraptor.crud.hypermedia;

import static com.google.common.io.Flushables.flushQuietly;
import static java.util.Collections.emptyList;
import static javax.enterprise.inject.spi.CDI.current;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Vetoed;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.serialization.gson.Exclusions;
import br.com.caelum.vraptor.serialization.gson.GsonSerializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Link;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.Gson;

/**
 * Seta a configuração para não envolver o json em uma tag root.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Vetoed
public class HypermediableGsonSerializer extends GsonSerializer {
	private final GsonSerializerBuilder builder;
	private final Writer writer;

	public HypermediableGsonSerializer(final GsonSerializerBuilder builder, final Writer writer, final TypeNameExtractor extractor) {
		super(builder, writer, extractor);
		this.builder = builder;
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public <T> Serializer from(final T object, final String alias) {
		super.from(object, alias);
		if (Collection.class.isInstance(object)) {
			// se a coleção estiver vazia, deve ser usado o root
			final Collection<?> collection = (Collection<?>) object;
			builder.setWithoutRoot(!collection.isEmpty());
		}
		return this;
	}

	@Override
	public void serialize() {
		final Object object = builder.getSerializee().getRoot();
		if (Collection.class.isInstance(object)) {
			final Collection<?> collection = (Collection<?>) object;
			if (collection.isEmpty()) {
				final Exclusions exclusions = new Exclusions(builder.getSerializee());
				builder.setExclusionStrategies(exclusions);
				final Gson gson = builder.create();
				final String alias = builder.getAlias();
				final Map<String, Object> map = new HashMap<>();
				map.put(alias, object);
				final Class<HypermediableRule> t = HypermediableRule.class;
				final HypermediableRule navigator = current().select(t).get();
				final String operation = current().select(String.class, OQ).get();
				final String resource = current().select(String.class, RQ).get();
				final Set<Link> links = navigator.from(emptyList(), resource, operation);
				map.put("links", links);
				if (PaginatedCollection.class.isInstance(object)) {
					final PaginatedCollection<?, ?> paginated = (PaginatedCollection<?, ?>) object;
					final Meta meta = paginated.unwrap(Meta.class);
					map.put("meta", meta);
				}
				gson.toJson(map, writer);
				flushQuietly(writer);
				return;
			}
		}
		super.serialize();
	}

	private static final Resource RQ = new Resource() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return Resource.class;
		}

		@Override
		public String value() {
			return "";
		}
	};
	private static final Operation OQ = new Operation() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return Operation.class;
		}

		@Override
		public String value() {
			return "";
		}
	};
}
