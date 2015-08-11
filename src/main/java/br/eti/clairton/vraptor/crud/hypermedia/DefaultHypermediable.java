package br.eti.clairton.vraptor.crud.hypermedia;

import static javax.enterprise.inject.spi.CDI.current;

import java.lang.annotation.Annotation;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;
import br.eti.clairton.vraptor.crud.serializer.Resourceable;

public class DefaultHypermediable<T> implements Hypermediable<T> , Resourceable{

	@Override
	public String getResource() {
		return current().select(String.class, RQ).get();
	}

	@Override
	public String getOperation() {
		return current().select(String.class, OQ).get();
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
