package com.ighor.api.e_commerce.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String string){
        super(string);
    }

    public DuplicateResourceException(Object objeto){
        super("Erro: ja ah regsitro dessa informacao especifica no sistema");
    }

    public DuplicateResourceException(){
        super("Erro: ja ah regsitro dessa informacao especifica no sistema");
    }
}
