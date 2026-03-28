package com.petromirdzhunev.json.converter.gson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.petromirdzhunev.json.conversion.api.JsonConverter;

public class GsonJsonConverter implements JsonConverter<Gson> {

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
	public Gson delegate() {
		return null;
	}
}
