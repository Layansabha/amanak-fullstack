package com.example.amanakk_backend;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FlexibleDateDeserializer extends JsonDeserializer<Date> {
    private static final String[] SUPPORTED_FORMATS = {
            "yyyy-MM-dd",
            "MMM d, yyyy, h:mm:ss a",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ssXXX"
    };

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.currentToken() == JsonToken.VALUE_NUMBER_INT) {
            return new Date(parser.getLongValue());
        }

        String value = parser.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        for (String format : SUPPORTED_FORMATS) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                dateFormat.setLenient(false);
                return dateFormat.parse(value.trim());
            } catch (ParseException ignored) {
                // Try the next supported frontend format.
            }
        }

        throw JsonMappingException.from(parser, "Unsupported date format: " + value);
    }
}
