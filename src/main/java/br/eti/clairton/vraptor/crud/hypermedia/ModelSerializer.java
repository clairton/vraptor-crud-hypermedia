package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;

import javax.enterprise.inject.Vetoed;
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

@Vetoed
public class ModelSerializer implements JsonSerializer<Model> {
	private final HypermediableSerializer<Model> delegate;
	private final JpaSerializer<Model> serializer;

	@Inject
	public ModelSerializer(final HypermediableRule navigator,
			final @Resource String resource, final @Operation String operation) {
		serializer = new JpaSerializer<Model>(new Mirror(),
				LogManager.getLogger(JpaSerializer.class)) {
		};
		delegate = new HypermediableSerializer<Model>(navigator, operation,
				resource, serializer) {
		};
	}

	public void addIgnoredField(final String field) {
		serializer.addIgnoredField(field);
	}

	@Override
	public JsonElement serialize(Model src, Type type,
			JsonSerializationContext context) {
		return delegate.serialize(src, type, context);
	}
}