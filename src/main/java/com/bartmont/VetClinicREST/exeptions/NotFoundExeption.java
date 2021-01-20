package com.bartmont.VetClinicREST.exeptions;

public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption() {
        super("Object not found");
    }
}
