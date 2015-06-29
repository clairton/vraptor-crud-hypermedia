package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import javax.enterprise.inject.Specializes;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.HypermediableSerializer;
import br.eti.clairton.jpa.serializer.Mode;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Specializes
public class ModelSerializer extends br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements JsonSerializer<Model> {
	private final HypermediableSerializer<Model> delegate;


	@Inject
	public ModelSerializer(final HypermediableRule navigator) {
		delegate = new HypermediableSerializer<Model>(navigator){

			@Override
			protected String getResource() {
				return CDI.current().select(String.class, RQ).get();
			}

			@Override
			protected String getOperation() {
				return CDI.current().select(String.class, OQ).get();
			}
		};
	}

	@Override
	public JsonElement serialize(final Model src, final Type type, final JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}

	@Override
	public Map<String, Mode> nodes() {
		return delegate.nodes();
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