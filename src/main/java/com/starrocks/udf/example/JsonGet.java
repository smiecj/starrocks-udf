package com.starrocks.udf.example;

import com.alibaba.fastjson.JSONPath;

// JsonGet: scalar udf
public class JsonGet {
    public final String evaluate(String obj, String key) {
        if (obj == null || key == null) return null;
        try {
            return JSONPath.read(obj, key).toString();
        } catch (Exception e) {
            return null;
        }
    }
}

// SELECT JsonGet('{"name": "smiecj"}', 'name');