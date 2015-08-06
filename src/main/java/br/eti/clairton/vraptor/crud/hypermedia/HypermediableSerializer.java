package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.Vetoed;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.security.Operation;
import br.eti.clairton.security.Resource;


@Vetoed
public class HypermediableSerializer<T> extends br.eti.clairton.gson.hypermedia.HypermediableSerializer<T> {
	private static final long serialVersionUID = 1L;

	public HypermediableSerializer(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		super(navigator, em, inflector);
	}

	@Override
	protected String getResource() {
		return CDI.current().select(String.class, RQ).get();
	}

	@Override
	protected String getOperation() {
		return CDI.current().select(String.class, OQ).get();
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
