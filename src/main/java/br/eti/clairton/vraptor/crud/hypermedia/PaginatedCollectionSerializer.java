package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;

import br.eti.clairton.repository.Meta;
import br.eti.clairton.repository.Model;
import br.eti.clairton.repository.PaginatedCollection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Vetoed
public class PaginatedCollectionSerializer<T, X> implements JsonSerializer<PaginatedCollection<T, X>> {
	private JsonSerializer<Collection<Model>> delegate;
	
	@Deprecated
	protected PaginatedCollectionSerializer() {
		System.out.println("sdgsagsdgsd");
	}
	
	@Inject
	public PaginatedCollectionSerializer(final JsonSerializer<Collection<Model>> delegate) {
		this.delegate = delegate;
	}	
	
	@Override
	public JsonElement serialize(final PaginatedCollection<T, X> src, final Type type, final JsonSerializationContext context) {
		@SuppressWarnings("unchecked")
		final PaginatedCollection<Model, X> collection = (PaginatedCollection<Model, X>) src;
		final JsonElement json = delegate.serialize(collection, type, context);
		final Iterator<T> iterator = src.iterator();
		if(iterator.hasNext() && Model.class.isInstance(iterator.next())){
			final JsonObject object = (JsonObject) json;
			final Meta meta = src.unwrap(Meta.class);
			final JsonElement element = context.serialize(meta);
			object.add("meta", element);
		}
		return json;
	}
}