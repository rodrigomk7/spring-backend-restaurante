package com.coderhouse.app.handler;

public class IdNotFoundException extends Exception {
    private String msg;

    public IdNotFoundException(){
        super("Id no existente");
    }
}
