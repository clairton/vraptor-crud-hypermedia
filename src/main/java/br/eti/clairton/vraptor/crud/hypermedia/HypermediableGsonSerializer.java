package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Writer;
import java.util.Collection;

import javax.enterprise.inject.Vetoed;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;
import br.eti.clairton.vraptor.crud.GsonSerializer;
import br.eti.clairton.vraptor.crud.serializer.TagableExtractor;

/**
 * Seta a configuração para não envolver o json em uma tag root.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Vetoed
@Deprecated
public class HypermediableGsonSerializer extends GsonSerializer {
	private final GsonSerializerBuilder builder;

	public HypermediableGsonSerializer(final GsonSerializerBuilder builder, final Writer writer, final TypeNameExtractor extractor, final TagableExtractor tagableExtractor) {
		super(builder, writer, extractor, tagableExtractor);
		this.builder = builder;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public <T> Serializer from(final T object) {
		super.from(object);
		if (Collection.class.isInstance(object)) {
			builder.setWithoutRoot(true);
		}
		return this;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public <T> Serializer from(final T object, final String alias) {
		super.from(object, alias);
		if (Collection.class.isInstance(object)) {
			builder.setWithoutRoot(true);
		}
		return this;
	}
}
