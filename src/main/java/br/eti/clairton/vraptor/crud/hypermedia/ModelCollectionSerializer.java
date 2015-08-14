package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.annotation.Priority;
import javax.inject.Inject;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableCollection;
import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.repository.Model;
import br.eti.clairton.vraptor.crud.serializer.Resourceable;
import br.eti.clairton.vraptor.crud.serializer.TagableExtractor;

@Priority(0)
public class ModelCollectionSerializer extends HypermediableCollectionSerializer<Model> implements JsonSerializer<Collection<Model>>, HypermediableCollection<Model>, Serializable, Resourceable {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Model> hypermediable;
	private final Inflector inflector;

	@Deprecated
	public ModelCollectionSerializer() {
		this(null, null, null);
	}

	@Inject
	public ModelCollectionSerializer(final HypermediableRule navigator, final TagableExtractor extractor, final Inflector inflector) {
		super(navigator, inflector);
		hypermediable = new DefaultHypermediable<>();
		this.inflector = inflector;
	}


	@Override
	public Class<Model> getCollectionType() {
		return Model.class;
	}

	@Override
	public String getResource() {
		return hypermediable.getResource();
	}

	@Override
	public String getOperation() {
		return hypermediable.getOperation();
	}
	
	@Override
	protected Boolean isResource(final Collection<Model> src) {
		return (!src.isEmpty() && Model.class.isInstance(getFirst(src))) && getRootTag(getFirst(src)).equals(getResource());
	}

	@Override
	public String getRootTagCollection(Collection<Model> collection) {
		return inflector.pluralize(getResource());
	}

	@Override
	public JsonElement serialize(final Collection<Model> src, final Type type, final JsonSerializationContext context) {
		return super.serialize(src, type, context);
	}
}