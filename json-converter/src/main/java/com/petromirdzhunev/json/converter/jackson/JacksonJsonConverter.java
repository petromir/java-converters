package com.petromirdzhunev.json.converter.jackson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.petromirdzhunev.json.conversion.api.JsonConversionException;
import com.petromirdzhunev.json.conversion.api.JsonConverter;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.CollectionLikeType;
import tools.jackson.databind.type.MapLikeType;
import tools.jackson.databind.type.TypeFactory;

public class JacksonJsonConverter implements JsonConverter {

	private final JsonMapper jsonMapper;
	private final TypeFactory typeFactory;

	public JacksonJsonConverter(final JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
		// This helps to reuse cached types;
		this.typeFactory = TypeFactory.createDefaultInstance();
	}

	@Override
	public <TYPE> String objectToJson(final TYPE object) {
		try {
			return jsonMapper.writer().writeValueAsString(object);
		} catch (JacksonException e) {
			throw new JsonConversionException("Failed to convert an Object to JSON [objetType=%s]"
					.formatted(object.getClass().getSimpleName()), e);
		}
	}

	@Override
	public <TYPE> TYPE jsonToObject(final String json, final Class<TYPE> type) {
		try {
			return jsonMapper.readValue(json, type);
		} catch (JacksonException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to type [json=%s, type=%s]".formatted(json, type.getSimpleName()), e);
		}
	}

	@Override
	public <TYPE> List<TYPE> jsonToList(final String json, final Class<TYPE> elementType) {
		final CollectionLikeType listType = typeFactory.constructCollectionLikeType(List.class, elementType);
		try {
			return jsonMapper.readValue(json, listType);
		} catch (JacksonException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to List [json=%s, listType=%s]".formatted(json,
							listType.toCanonical()), e);
		}
	}

	@Override
	public <KEY, VALUE> Map<KEY, VALUE> jsonToMap(final String json, final Class<KEY> keyType,
			final Class<VALUE> valueType) {
		final MapLikeType mapType = typeFactory.constructMapLikeType(Map.class, keyType, valueType);
		try {
			return jsonMapper.readValue(json, mapType);
		} catch (JacksonException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to Map [json=%s, mapType=%s]".formatted(json, mapType.toCanonical()), e);
		}
	}

	@Override
	public <TYPE> Set<TYPE> jsonToSet(final String json, final Class<TYPE> setElementType) {
		final CollectionLikeType setType = typeFactory.constructCollectionLikeType(Set.class, setElementType);
		try {
			return jsonMapper.readValue(json, setType);
		} catch (JacksonException e) {
			throw new JsonConversionException(
					"Failed to convert JSON to Set [json=%s, setType=%s]".formatted(json, setType.toCanonical()), e);
		}
	}

	@Override
	public JsonMapper delegate() {
		return jsonMapper;
	}
}
