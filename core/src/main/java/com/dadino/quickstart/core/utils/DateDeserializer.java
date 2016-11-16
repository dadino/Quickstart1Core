package com.dadino.quickstart.core.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date> {

	private static final String[] DATE_FORMATS =
			new String[]{"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss"};


	@Override
	public Date deserialize(JsonElement jsonElement, Type typeOF,
	                        JsonDeserializationContext context) throws
			JsonParseException {
		for (String format : DATE_FORMATS) {
			try {
				return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
			} catch (ParseException e) {
			}
		}
		throw new JsonParseException(
				"Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " +
				Arrays.toString(DATE_FORMATS));
	}
}