package com.quickblox.sample.videochat.java.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;


public class JsonUtils {
    static int mCounter = 0;

    public static String getJsonString(Object obj) {
        Gson lGson = new GsonBuilder().setDateFormat(ServiceConstants.SERVICE_DATE_FORMAT).create();
        return lGson.toJson(obj);
    }

    public static JsonObject getJsonObject(String obj) {
        JsonParser lParser = new JsonParser();
        return lParser.parse(obj).getAsJsonObject();
    }

    public static JsonObject getJsonArrayObject(JsonArray obj) {
        JsonParser lParser = new JsonParser();
        return lParser.parse(String.valueOf(obj)).getAsJsonObject();
    }

    public static JsonArray getJsonArray(String obj) {
        JsonParser lParser = new JsonParser();
        return lParser.parse(obj).getAsJsonArray();
    }

    public static boolean isJsonValid(String json) {
        try {
            JsonParser lParser = new JsonParser();
            lParser.parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Object getObjectFromJsonObject(String jsonElement, Type model) {
        Gson lGson = new GsonBuilder().setDateFormat(ServiceConstants.SERVICE_DATE_FORMAT).create();
        return lGson.fromJson(jsonElement, model);
    }
}
