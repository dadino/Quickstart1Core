package com.dadino.quickstart.core.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date> {

    private static final String[] DATE_FORMATS = new String[]{"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss"};

    private final String[] dateFormats;

    public DateDeserializer(String[] dateFormats) {
        this.dateFormats = dateFormats;
    }

    public DateDeserializer() {
        this.dateFormats = DATE_FORMATS;
    }

    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
            throws JsonParseException {
        for (String format : dateFormats) {
            try {
                return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
            } catch (Exception ignored) {
            }
        }
        throw new JsonParseException(
                "Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " + Arrays.toString(dateFormats));
    }
}