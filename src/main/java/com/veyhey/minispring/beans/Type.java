package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.XMLParseException;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Type {

    private static final Map<String, Type> DEFAULT_TYPES = Map.of(
            "int", new Type(int.class),
            "String", new Type(String.class),
            "Integer", new Type(Integer.class)
    );

    private static final Map<String, Type> typeCache = new ConcurrentHashMap<>();

    static {
        typeCache.putAll(DEFAULT_TYPES);
    }

    private Class clazz;

    public Type(Class clazz) {
        this.clazz = clazz;
    }


    public Class getClazz() {
        return clazz;
    }

    public static Type parseType(String token) throws XMLParseException {
        var type = typeCache.get(token);
        if (type == null) {
            try {
                type = new Type(Class.forName(token));
                typeCache.put(token, type);
            } catch (ClassNotFoundException e) {
                throw new XMLParseException(e);
            }
        }
        return type;
    }

    public Object convertToRealType(String value) {
        if (int.class.equals(this.clazz)) {
            return Integer.parseInt(value);
        } else if (Integer.class.equals(this.clazz)) {
            return Integer.valueOf(value);
        } else if (String.class.equals(this.clazz)) {
            return value;
        }
        return value;
    }

}