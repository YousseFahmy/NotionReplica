package com.notionreplica.searchapp.exceptions;

public class NoSearchResultsException extends RuntimeException {
    public NoSearchResultsException(String message) {
        super(message);
    }
}
