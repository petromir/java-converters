package com.petromirdzhunev.json.converter.jackson;

import com.petromirdzhunev.json.conversion.api.JsonConverter;
import com.petromirdzhunev.json.converter.AbstractJsonConverterTest;

import tools.jackson.databind.json.JsonMapper;

public class JacksonJsonConverterTest extends AbstractJsonConverterTest {

	@Override
	protected JsonConverter converter() {
		return new JacksonJsonConverter(new JsonMapper());
	}
}