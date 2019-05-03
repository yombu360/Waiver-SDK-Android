package com.yombu.waiverlibrary;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;

class LibraryResponseConverter {

    public static ModelMyErrorResponse convertToErrorResponse(ResponseBody error) {
        JsonParser parser = new JsonParser();
        JsonElement mJson = null;
        try {
            mJson = parser.parse(error.string());
            Gson gson = new Gson();
            return gson.fromJson(mJson, ModelMyErrorResponse.class);
        } catch (IOException ex) {
        }
        return null;
    }

}
