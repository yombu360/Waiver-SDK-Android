package com.yombu.waiverlibrary;

import java.util.HashMap;

class ModelMyTextMap<T> extends HashMap<String, T> {
    @Override
    public T get(Object key) {
        if (super.get(key) == null) {
            return super.get("en");
        }
        return super.get(key);
    }
}
