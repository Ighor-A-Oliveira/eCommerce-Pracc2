package com.ighor.api.e_commerce.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String string){
        super(string);
    }

    public BadRequestException(Object objeto){
        super("A requisicao nao esta de acordo com os parametros que o sistema estava esperando");
    }

    public BadRequestException(){
        super("A requisicao nao esta de acordo com os parametros que o sistema estava esperando");
    }
}
