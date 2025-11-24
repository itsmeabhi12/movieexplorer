package com.abhishek.movieexplorer.exception;

public class TmdbApiException extends RuntimeException {

    public TmdbApiException(String message) {
        super(message);
    }
}
