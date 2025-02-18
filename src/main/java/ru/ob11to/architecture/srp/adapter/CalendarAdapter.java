package ru.ob11to.architecture.srp.adapter;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarAdapter implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {
    private final DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");

    @Override
    public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext context) {
        // Преобразуем Calendar в строку по заданному формату
        return new JsonPrimitive(dateFormat.format(src.getTime()));
    }

    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(json.getAsString()));
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
        return calendar;
    }
}
