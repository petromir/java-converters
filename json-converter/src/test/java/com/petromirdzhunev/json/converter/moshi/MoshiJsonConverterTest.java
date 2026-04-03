package com.petromirdzhunev.json.converter.moshi;

import com.petromirdzhunev.json.conversion.api.JsonConverter;
import com.petromirdzhunev.json.converter.AbstractJsonConverterTest;
import com.squareup.moshi.Moshi;

public class MoshiJsonConverterTest extends AbstractJsonConverterTest {

	@Override
	protected JsonConverter converter() {
		return new MoshiJsonConverter(new Moshi.Builder().build());
	}
}