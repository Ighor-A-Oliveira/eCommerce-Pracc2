package com.ighor.api.e_commerce.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String string){
        super(string);
    }

    public ResourceNotFoundException(Object objeto){
        super("O recurso solicitado nao foi encontrado");
    }

    public ResourceNotFoundException(){
        super("O recurso solicitado nao foi encontrado");
    }
}
