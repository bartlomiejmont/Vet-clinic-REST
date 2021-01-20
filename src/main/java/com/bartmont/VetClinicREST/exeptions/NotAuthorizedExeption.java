package com.bartmont.VetClinicREST.exeptions;

public class NotAuthorizedExeption extends RuntimeException {
    public NotAuthorizedExeption(){
        super("You are not authorized");
    }
}
