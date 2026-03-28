package com.petromirdzhunev.json.converter.moshi;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.petromirdzhunev.json.conversion.api.JsonConverter;
import com.squareup.moshi.Moshi;

public class MoshiJsonConverter implements JsonConverter<Moshi> {

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
	public Moshi delegate() {
		return null;
	}
}
