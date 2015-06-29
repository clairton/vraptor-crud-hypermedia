package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ModelCollectionSerializer implements JsonSerializer<Collection<Model>> {
	private final HypermediableCollectionSerializer<Model> delegate;

	@Deprecated
	public ModelCollectionSerializer() {
		this(null, null);
	}

	@Inject
	public ModelCollectionSerializer(final HypermediableRule navigator, final Inflector inflector) {
		delegate = new HypermediableCollectionSerializer<Model>(navigator, inflector) {

			@Override
			protected Class<Model> getCollectionType() {
				return Model.class;
			}

			@Override
			protected String getResource() {
				return CDI.current().select(String.class, RQ).get();
			}

			@Override
			protected String getOperation() {
				return CDI.current().select(String.class, OP).get();
			}
		};
	}

	@Override
	public JsonElement serialize(final Collection<Model> src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
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
	private static final Operation OP = new Operation() {

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