package com.swyg.oneului.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyg.oneului.model.Weather;
import com.swyg.oneului.util.WeatherDictionary;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class WeatherDeserializer extends JsonDeserializer<Weather.Items> {
    private final ObjectMapper objectMapper;
    private static final String RESPONSE = "response";
    private static final String BODY = "body";
    private static final String ITEMS = "items";
    private static final String ITEM = "item";

    @Override
    public Weather.Items deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode responseNode = node.findValue(RESPONSE);

        JsonNode itemNode = responseNode.get(BODY).get(ITEMS).get(ITEM);
        List<Weather.Item> items = Arrays.stream(objectMapper.treeToValue(itemNode, Weather.Item[].class)).toList();

        List<Weather.Item> weathers = new ArrayList<>();
        for (Weather.Item item : items) {
            if (WeatherDictionary.isRelatedWeatherCode(item.getCategory())) {
                item.initConvertCategory(WeatherDictionary.convertWeatherCode(item.getCategory()));
                item.initConvertFcstValue(WeatherDictionary.convertWeatherValue(item.getCategory(), item.getFcstValue()));
                weathers.add(item);
            }
        }

        return new Weather.Items(weathers);
    }
}
