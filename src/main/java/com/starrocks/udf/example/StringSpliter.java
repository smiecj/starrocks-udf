package com.starrocks.udf.example;

// StringSpliter: UDTF
public class StringSpliter {
    public String[] process(String in) {
        if (in == null) return null;
        return in.split(" ");
    }
}

// SELECT id, StringSplit FROM test_string, StringSplit(name); 