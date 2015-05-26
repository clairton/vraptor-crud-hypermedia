package br.eti.clairton.vraptor.crud.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.inject.Inject;

import br.eti.clairton.repository.Meta;
import br.eti.clairton.repository.Model;
import br.eti.clairton.repository.PaginatedCollection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
		final JsonObject json = (JsonObject) delegate.serialize(collection, type, context);
		json.add("meta", context.serialize(src.unwrap(Meta.class)));
		return json;
	}
}