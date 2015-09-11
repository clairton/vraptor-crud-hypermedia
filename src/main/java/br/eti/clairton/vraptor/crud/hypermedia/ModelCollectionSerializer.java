package br.eti.clairton.vraptor.crud.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.annotation.Priority;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import br.com.caelum.vraptor.controller.BeanClass;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.gson.hypermedia.HypermediableCollection;
import br.eti.clairton.gson.hypermedia.HypermediableCollectionSerializer;
import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.repository.Model;
import br.eti.clairton.security.Extractor;
import br.eti.clairton.vraptor.crud.serializer.Resourceable;
import br.eti.clairton.vraptor.crud.serializer.TagableExtractor;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Priority(0)
public class ModelCollectionSerializer extends HypermediableCollectionSerializer<Model> implements JsonSerializer<Collection<Model>>, HypermediableCollection<Model>, Serializable, Resourceable<Collection<Model>> {
	private static final long serialVersionUID = 1L;
	private final Hypermediable<Collection<Model>> hypermediable;
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

//	@Override
//	public String getResource(final Collection<Model> src) {
//		return hypermediable.getResource(src);
//	}

	@Override
	public String getOperation(final Collection<Model> src) {
		return hypermediable.getOperation(src);
	}
	
	@Override
	protected Boolean isResource(final Collection<Model> src) {
		return (!src.isEmpty() && Model.class.isInstance(getFirst(src))) && getRootTag(getFirst(src)).equals(getResource(src)) 
					|| (src.isEmpty() && PaginatedCollection.class.isInstance(src)  && getResource().equals(getResource(src)) );
	}

	private String getResource() {
		try{
			final BeanClass beanClass = CDI.current().select(ControllerMethod.class).get().getController();
			final Object controller = beanClass.getType().newInstance();
			final Extractor extractor = CDI.current().select(Extractor.class).get();
			final String resource = extractor.getResource(controller);
			return resource;
		}catch(final Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getRootTagCollection(Collection<Model> src) {
		return inflector.pluralize(getResource(src));
	}

	@Override
	public JsonElement serialize(final Collection<Model> src, final Type type, final JsonSerializationContext context) {
		return super.serialize(src, type, context);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getResource(@SuppressWarnings("rawtypes") Collection src) {
		return hypermediable.getResource(src);
	}
}