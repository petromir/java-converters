package com.petromirdzhunev.json.converter.jackson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.petromirdzhunev.json.conversion.api.JsonConverter;

import tools.jackson.databind.json.JsonMapper;

public class JacksonJsonConverter implements JsonConverter {

	@Override
	public <TYPE> String objectToJson(final TYPE type) {
		return "";
	}

	@Override
	public <TYPE> TYPE jsonToObject(final String s, final Class<TYPE> aClass) {
		return null;
	}

	@Override
	public <TYPE> List<TYPE> jsonToList(final String s, final Class<TYPE> aClass) {
		return List.of();
	}

	@Override
	public <KEY, VALUE> Map<KEY, VALUE> jsonToMap(final String s, final Class<KEY> aClass, final Class<VALUE> aClass1) {
		return Map.of();
	}

	@Override
	public <TYPE> Set<TYPE> jsonToSet(final String s, final Class<TYPE> aClass) {
		return Set.of();
	}

	@Override
	public JsonMapper delegate() {
		return null;
	}
}
