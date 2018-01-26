package com.yksoul.enums;

/**
 * @author yk
 * @version 1.0 Date: 2018-01-26
 */
public enum TkGeneratedValue {
    JDBC("generator = \"JDBC\""),
    IDENTITY("strategy = GenerationType.IDENTITY"),
    UUID("generator = \"UUID\""),
    ORACLE("strategy = GenerationType.IDENTITY, generator = \"select SEQ_ID.nextval from dual\"");

    private String value;

    TkGeneratedValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
