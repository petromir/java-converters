package com.petromirdzhunev.json.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.petromirdzhunev.json.conversion.api.JsonConversionException;
import com.petromirdzhunev.json.conversion.api.JsonConverter;

public abstract class AbstractJsonConverterTest {

	private static final Person PERSON = new Person("John Doe", 30);
	// We match substrings for serialization to avoid brittle tests caused by key-ordering differences between libraries.
	private static final String PERSON_JSON_EXPECTED_PROPERTIES_NAME = """
			"name":"John Doe"\
			""";
	private static final String PERSON_JSON_EXPECTED_PROPERTIES_AGE = """
			"age":30\
			""";
	private static final String PERSON_JSON = """
			{
			  "name": "John Doe",
			  "age": 30
			}""";

	private static final String INVALID_JSON = """
			{
			  "invalid"
			}""";

	protected abstract JsonConverter converter();

	@Test
	void shouldConvertObjectToJson() {
		final String json = converter().objectToJson(PERSON);
		assertThat(json)
				.contains(PERSON_JSON_EXPECTED_PROPERTIES_NAME)
				.contains(PERSON_JSON_EXPECTED_PROPERTIES_AGE);
	}

	@Test
	void shouldConvertJsonToObject() {
		final Person person = converter().jsonToObject(PERSON_JSON, Person.class);
		assertThat(person).isEqualTo(PERSON);
	}

	@Test
	void shouldConvertJsonToList() {
		final String listJson = """
				[
				  {
				    "name": "Alice",
				    "age": 25
				  },
				  {
				    "name": "Bob",
				    "age": 28
				  }
				]""";
		final List<Person> expectedList = List.of(new Person("Alice", 25), new Person("Bob", 28));

		final List<Person> list = converter().jsonToList(listJson, Person.class);
		assertThat(list).isEqualTo(expectedList);
	}

	@Test
	void shouldConvertJsonToMap() {
		final String mapJson = """
				{
				  "user1": {
				    "name": "Alice",
				    "age": 25
				  },
				  "user2": {
				    "name": "Bob",
				    "age": 28
				  }
				}""";
		final Map<String, Person> expectedMap = Map.of(
				"user1", new Person("Alice", 25),
				"user2", new Person("Bob", 28)
		);

		final Map<String, Person> map = converter().jsonToMap(mapJson, String.class, Person.class);
		assertThat(map).isEqualTo(expectedMap);
	}

	@Test
	void shouldConvertJsonToSet() {
		final String setJson = """
				[
				  {
				    "name": "Alice",
				    "age": 25
				  },
				  {
				    "name": "Bob",
				    "age": 28
				  }
				]""";
		final Set<Person> expectedSet = Set.of(new Person("Alice", 25), new Person("Bob", 28));

		final Set<Person> set = converter().jsonToSet(setJson, Person.class);
		assertThat(set).isEqualTo(expectedSet);
	}

	@Test
	void shouldThrowExceptionWhenConvertingInvalidJsonToObject() {
		assertThatThrownBy(() -> converter().jsonToObject(INVALID_JSON, Person.class))
				.isInstanceOf(JsonConversionException.class);
	}

	@Test
	void shouldThrowExceptionWhenConvertingInvalidJsonToList() {
		assertThatThrownBy(() -> converter().jsonToList(INVALID_JSON, Person.class))
				.isInstanceOf(JsonConversionException.class);
	}

	@Test
	void shouldThrowExceptionWhenConvertingInvalidJsonToMap() {
		assertThatThrownBy(() -> converter().jsonToMap(INVALID_JSON, String.class, Person.class))
				.isInstanceOf(JsonConversionException.class);
	}

	@Test
	void shouldThrowExceptionWhenConvertingInvalidJsonToSet() {
		assertThatThrownBy(() -> converter().jsonToSet(INVALID_JSON, Person.class))
				.isInstanceOf(JsonConversionException.class);
	}

	public record Person(String name, int age) {
	}
}