package br.eti.clairton.vraptor.crud.hypermedia;

import static java.util.Collections.singletonList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.serialization.Serializee;
import br.com.caelum.vraptor.serialization.gson.Exclusions;
import br.com.caelum.vraptor.serialization.gson.RegisterStrategy;
import br.com.caelum.vraptor.serialization.gson.RegisterType;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

@Specializes
public class GsonBuilderWrapper  extends br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper{
	private final Iterable<JsonSerializer<?>> jsonSerializers;
	private final Iterable<JsonDeserializer<?>> jsonDeserializers;
	private List<ExclusionStrategy> exclusions;
	
	@Inject
	public GsonBuilderWrapper(@Any Instance<JsonSerializer<?>> jsonSerializers, 
			@Any Instance<JsonDeserializer<?>> jsonDeserializers,
			Serializee serializee) {
		super(jsonSerializers, jsonDeserializers, serializee);
		this.jsonSerializers = jsonSerializers;
		this.jsonDeserializers = jsonDeserializers;
		ExclusionStrategy exclusion = new Exclusions(serializee);
		exclusions = singletonList(exclusion);

	}
	
	
	public Gson create() {
		for (JsonSerializer<?> adapter : jsonSerializers) {
			registerAdapter(getAdapterType(adapter), adapter);
		}

		for (JsonDeserializer<?> adapter : jsonDeserializers) {
			registerAdapter(getAdapterType(adapter), adapter);
		}
		
		for (ExclusionStrategy exclusion : exclusions) {
			getGsonBuilder().addSerializationExclusionStrategy(exclusion);
		}
		
		return getGsonBuilder().create();
	}
	
	private void registerAdapter(Class<?> adapterType, Object adapter) {
		RegisterStrategy registerStrategy = adapter.getClass().getAnnotation(RegisterStrategy.class);
		if ((registerStrategy != null) && (registerStrategy.value().equals(RegisterType.SINGLE))) {
			getGsonBuilder().registerTypeAdapter(adapterType, adapter);
		} else {
			getGsonBuilder().registerTypeHierarchyAdapter(adapterType, adapter);
		}	
	}	
	
	private Class<?> getAdapterType(Object adapter) {
		Class<?> klazz = adapter.getClass();
		if(klazz.getName().contains("$Proxy$")){
			String[] split = klazz.getName().split("\\$Proxy\\$");
			try {
				klazz = Class.forName(split[0]);
			} catch (ClassNotFoundException e) {}
		}
		Type[] genericInterfaces = klazz.getGenericInterfaces();
		ParameterizedType type = (ParameterizedType) genericInterfaces[0];
		Type actualType = type.getActualTypeArguments()[0];

		if (actualType instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) actualType).getRawType();
		} else {
			return (Class<?>) actualType;
		}
	}
	
}
