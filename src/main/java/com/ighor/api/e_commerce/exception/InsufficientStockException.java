package com.ighor.api.e_commerce.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String string){
        super(string);
    }

    public InsufficientStockException(Object objeto){
        super("Nao ha estoque o suficiente do produto solicitado");
    }

    public InsufficientStockException(){
        super("Nao ha estoque o suficiente do produto solicitado");
    }
}
