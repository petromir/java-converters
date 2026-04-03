package com.petromirdzhunev.json.converter.gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.petromirdzhunev.json.conversion.api.JsonConversionException;
import com.petromirdzhunev.json.conversion.api.JsonConverter;

public class GsonJsonConverter implements JsonConverter {

	private final Gson gson;
	private final Map<Class<?>, Type> listTypeCache;
	private final Map<Class<?>, Type> setTypeCache;
	private final Map<Map.Entry<Class<?>, Class<?>>, Type> mapTypeCache;

	public GsonJsonConverter(final Gson gson) {
		this.gson = gson;
		// Optimize initial capacity and concurrency level based on available CPUs.
		final int cores = Runtime.getRuntime().availableProcessors();
		this.listTypeCache = new ConcurrentHashMap<>(32, 0.75f, cores);
		this.setTypeCache = new ConcurrentHashMap<>(32, 0.75f, cores);
		this.mapTypeCache = new ConcurrentHashMap<>(32, 0.75f, cores);
	}

	@Override
	public <TYPE> String objectToJson(final TYPE object) {
		try {
			return gson.toJson(object);
		} catch (JsonParseException e) {
			throw new JsonConversionException("Failed to convert an Object to JSON [objetType=%s]"
					.formatted(object.getClass().getSimpleName()), e);
		}
	}

	@Override
	public <TYPE> TYPE jsonToObject(final String json, final Class<TYPE> type) {
		try {
			return gson.fromJson(json, type);
		} catch (JsonParseException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to type [json=%s, type=%s]".formatted(json, type.getSimpleName()), e);
		}
	}

	@Override
	public <TYPE> List<TYPE> jsonToList(final String json, final Class<TYPE> elementType) {
		final Type listType = listTypeCache.computeIfAbsent(elementType,
				targetType -> TypeToken.getParameterized(List.class, targetType).getType());
		try {
			return gson.fromJson(json, listType);
		} catch (JsonParseException e) {
			throw new JsonConversionException(
					"Error while converting JSON to List [json=%s, listType=%s]".formatted(json,
							listType.getTypeName()), e);
		}
	}

	@Override
	public <KEY, VALUE> Map<KEY, VALUE> jsonToMap(final String json, final Class<KEY> keyType,
			final Class<VALUE> valueType) {
		final Type mapType = mapTypeCache.computeIfAbsent(Map.entry(keyType, valueType),
				entry -> TypeToken.getParameterized(Map.class, entry.getKey(), entry.getValue()).getType());
		try {
			return gson.fromJson(json, mapType);
		} catch (JsonParseException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to Map [json=%s, mapType=%s]".formatted(json, mapType.getTypeName()), e);
		}
	}

	@Override
	public <TYPE> Set<TYPE> jsonToSet(final String json, final Class<TYPE> setElementType) {
		final Type setType = setTypeCache.computeIfAbsent(setElementType,
				targetType -> TypeToken.getParameterized(Set.class, targetType).getType());
		try {
			return gson.fromJson(json, setType);
		} catch (JsonParseException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to Set [json=%s, setType=%s]".formatted(json, setType.getTypeName()), e);
		}
	}

	@Override
	public Gson delegate() {
		return gson;
	}
}
