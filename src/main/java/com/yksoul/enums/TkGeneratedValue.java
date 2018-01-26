package com.yksoul.enums;

/**
 * @author yk
 * @version 1.0 Date: 2018-01-26
 */
public enum TkGeneratedValue {
    JDBC("@GeneratedValue(generator = \"JDBC\")"),
    IDENTITY("@GeneratedValue(strategy = GenerationType.IDENTITY)"),
    UUID("@GeneratedValue(generator = \"UUID\")"),
    ORACLE("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"{0}\")");

    private String value;

    TkGeneratedValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
