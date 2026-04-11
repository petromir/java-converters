package com.petromirdzhunev.json.converter.gson;

import com.google.gson.Gson;
import com.petromirdzhunev.json.conversion.api.JsonConverter;
import com.petromirdzhunev.json.converter.AbstractJsonConverterTest;

public class GsonJsonConverterTest extends AbstractJsonConverterTest {

	@Override
	protected JsonConverter converter() {
		return new GsonJsonConverter(new Gson());
	}
}
