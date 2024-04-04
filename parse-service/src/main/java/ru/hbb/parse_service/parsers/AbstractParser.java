package ru.hbb.parse_service.parsers;

import lombok.Getter;

@Getter
public abstract class AbstractParser {

    protected final String URL;
    protected final String LANG;
    protected final int TIMEOUT;

    public AbstractParser(String URL, String LANG, int TIMEOUT) {
        this.URL = URL;
        this.LANG = LANG;
        this.TIMEOUT = TIMEOUT;
    }
}
