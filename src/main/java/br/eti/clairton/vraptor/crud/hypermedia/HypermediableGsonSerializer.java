package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Writer;
import java.util.Collection;

import javax.enterprise.inject.Vetoed;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;

/**
 * Seta a configuração para não envolver o json em uma tag root.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Vetoed
public class HypermediableGsonSerializer extends GsonSerializer {
	private final GsonSerializerBuilder builder;

	public HypermediableGsonSerializer(GsonSerializerBuilder builder,
			Writer writer, TypeNameExtractor extractor) {
		super(builder, writer, extractor);
		this.builder = builder;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public <T> Serializer from(final T object, final String alias) {
		super.from(object, alias);
		if (Collection.class.isInstance(object)) {
			builder.setWithoutRoot(Boolean.TRUE);
		}
		return this;
	}
}
