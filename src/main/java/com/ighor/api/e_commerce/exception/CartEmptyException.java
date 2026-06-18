package com.ighor.api.e_commerce.exception;

public class CartEmptyException extends RuntimeException{
    public CartEmptyException(String string){
        super(string);
    }

    public CartEmptyException(Object objeto){
        super("O carrinho esta vazio");
    }

    public CartEmptyException(){
        super("O carrinho esta vazio");
    }
}
