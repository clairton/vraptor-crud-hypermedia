package br.eti.clairton.vraptor.crud.hypermedia;

import static javax.enterprise.inject.spi.CDI.current;

import java.lang.annotation.Annotation;
import java.util.Collection;

import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;

public class DefaultHypermediable<T> implements HypermediableResourceable<T>{

	@Override
	public String getResource(final T src) {
		return getResource();
	}

	@Override
	public String getResource(final Collection<T> src) {
		return getResource();
	}

	public String getResource() {
		return current().select(String.class, RQ).get();
	}

	@Override
	public String getOperation(final T src) {
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
