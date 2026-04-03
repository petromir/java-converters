package com.petromirdzhunev.json.converter.moshi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.petromirdzhunev.json.conversion.api.JsonConversionException;
import com.petromirdzhunev.json.conversion.api.JsonConverter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

public class MoshiJsonConverter implements JsonConverter {

	private static final int ADAPTER_CACHE_INITIAL_CAPACITY = 64;
	private static final int TYPE_CACHE_INITIAL_CAPACITY = 32;
	private static final float CACHE_LOAD_FACTOR = 0.75f;

	private final Moshi moshi;
	private final Map<Type, JsonAdapter<?>> adapterCache;
	private final Map<Class<?>, Type> listTypeCache;
	private final Map<Class<?>, Type> setTypeCache;
	private final Map<Map.Entry<Class<?>, Class<?>>, Type> mapTypeCache;

	public MoshiJsonConverter(final Moshi moshi) {
		this.moshi = moshi;
		// Optimize initial capacity and concurrency level based on available CPUs.
		final int cores = Runtime.getRuntime().availableProcessors();
		this.adapterCache = new ConcurrentHashMap<>(ADAPTER_CACHE_INITIAL_CAPACITY, CACHE_LOAD_FACTOR, cores);
		this.listTypeCache = new ConcurrentHashMap<>(TYPE_CACHE_INITIAL_CAPACITY, CACHE_LOAD_FACTOR, cores);
		this.setTypeCache = new ConcurrentHashMap<>(TYPE_CACHE_INITIAL_CAPACITY, CACHE_LOAD_FACTOR, cores);
		this.mapTypeCache = new ConcurrentHashMap<>(TYPE_CACHE_INITIAL_CAPACITY, CACHE_LOAD_FACTOR, cores);
	}

	@Override
	public <TYPE> String objectToJson(final TYPE object) {
		final JsonAdapter<TYPE> adapter = adapter(object.getClass());
		return adapter.toJson(object);
	}

	@Override
	public <TYPE> TYPE jsonToObject(final String json, final Class<TYPE> type) {
		final JsonAdapter<TYPE> adapter = adapter(type);
		try {
			return adapter.fromJson(json);
		} catch (IOException | JsonDataException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to type [json=%s, type=%s]".formatted(json, type.getSimpleName()), e);
		}
	}

	@Override
	public <TYPE> List<TYPE> jsonToList(final String json, final Class<TYPE> elementType) {
		final Type listType = listTypeCache.computeIfAbsent(elementType,
				targetType -> Types.newParameterizedType(List.class, targetType));
		final JsonAdapter<List<TYPE>> adapter = adapter(listType);
		try {
			return adapter.fromJson(json);
		} catch (IOException | JsonDataException e) {
			throw new JsonConversionException(
					"Error while converting JSON to List [json=%s, listType=%s]".formatted(json, listType.getTypeName()), e);
		}
	}

	@Override
	public <KEY, VALUE> Map<KEY, VALUE> jsonToMap(final String json, final Class<KEY> keyType,
			final Class<VALUE> valueType) {
		final Type mapType = mapTypeCache.computeIfAbsent(Map.entry(keyType, valueType),
				entry -> Types.newParameterizedType(Map.class, entry.getKey(), entry.getValue()));
		final JsonAdapter<Map<KEY, VALUE>> adapter = adapter(mapType);
		try {
			return adapter.fromJson(json);
		} catch (IOException | JsonDataException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to Map [json=%s, mapType=%s]".formatted(json, mapType.getTypeName()), e);
		}
	}

	@Override
	public <TYPE> Set<TYPE> jsonToSet(final String json, final Class<TYPE> setElementType) {
		final Type setType = setTypeCache.computeIfAbsent(setElementType,
				targetType -> Types.newParameterizedType(Set.class, targetType));
		final JsonAdapter<Set<TYPE>> adapter = adapter(setType);
		try {
			return adapter.fromJson(json);
		} catch (IOException | JsonDataException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to Set [json=%s, setType=%s]".formatted(json, setType.getTypeName()), e);
		}
	}

	@Override
	public Moshi delegate() {
		return moshi;
	}

	@SuppressWarnings("unchecked")
	private <TYPE> JsonAdapter<TYPE> adapter(final Type type) {
		return (JsonAdapter<TYPE>) adapterCache.computeIfAbsent(type, moshi::adapter);
	}
}
