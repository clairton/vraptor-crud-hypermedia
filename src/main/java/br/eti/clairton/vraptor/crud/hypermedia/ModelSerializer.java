package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.logging.log4j.LogManager;

import br.eti.clairton.jpa.serializer.JpaSerializer;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.hypermedia.HypermediableRule;
import br.eti.clairton.vraptor.hypermedia.HypermediableSerializer;
import br.eti.clairton.vraptor.hypermedia.Operation;
import br.eti.clairton.vraptor.hypermedia.Resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Specializes
@Dependent
public class ModelSerializer extends
		br.eti.clairton.vraptor.crud.serializer.ModelSerializer implements
		JsonSerializer<Model> {
	private final HypermediableSerializer<Model> delegate;
	private final JpaSerializer<Model> jpaSerializer;

	@Inject
	public ModelSerializer(final HypermediableRule navigator,
			@Resource String resource, @Operation String operation) {
		super(new Mirror());
		jpaSerializer = new JpaSerializer<Model>(new Mirror(),
				LogManager.getLogger(JpaSerializer.class)) {
		};
		delegate = new HypermediableSerializer<Model>(navigator, operation,
				resource, jpaSerializer) {
		};
	}

	@Override
	public void addIgnoredField(String field) {
		jpaSerializer.addIgnoredField(field);
	}

	@Override
	public JsonElement serialize(Model src, Type type,
			JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}