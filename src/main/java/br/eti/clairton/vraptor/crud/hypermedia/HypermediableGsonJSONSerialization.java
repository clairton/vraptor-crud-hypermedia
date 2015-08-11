package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Collection;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;
import br.eti.clairton.vraptor.crud.GsonJSONSerialization;
import br.eti.clairton.vraptor.crud.serializer.TagableExtractor;

/**
 * Seta a configuração para não envolver o json em uma tag root.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Specializes
public class HypermediableGsonJSONSerialization extends GsonJSONSerialization {
	private GsonSerializerBuilder builder;
//	private Writer writer;
//	private TypeNameExtractor extractor;
//	private TagableExtractor tagableExtractor;

	@Inject
	public HypermediableGsonJSONSerialization(final HttpServletResponse response, final TypeNameExtractor extractor, final GsonSerializerBuilder builder, final Environment environment, final TagableExtractor tagableExtractor) {
		super(response, extractor, builder, environment, tagableExtractor);
//		this.extractor = extractor;
		this.builder = builder;
//		this.tagableExtractor = tagableExtractor;
//		try {
//			this.writer = response.getWriter();
//		} catch (final IOException e) {
//			throw new ResultException("Unable to serialize data", e);
//		}
	}

//	/**
//	 * {@inheritDoc}.
//	 */
//	@Override
//	protected SerializerBuilder getSerializer() {
//		return new HypermediableGsonSerializer(builder, writer, extractor, tagableExtractor);
//	}
//	
//	@Override
//	public <T> Serializer from(T object, String alias) {
//		final Serializer  serializer = super.from(object, alias);
//		if(Collection.class.isInstance(object)){
//			builder.setWithoutRoot(true);			
//		}
//		return serializer;
//	}

	@Override
	public <T> Serializer from(final T object) {
		final Serializer  serializer = super.from(object);
		if(Collection.class.isInstance(object)){
			builder.setWithoutRoot(true);			
		}
		return serializer;
	}
}
