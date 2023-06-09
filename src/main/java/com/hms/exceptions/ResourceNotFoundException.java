package com.hms.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private String field;
    private String fieldId;
    private int id;
    public ResourceNotFoundException(String field,String fieldId,int id){
        super(String.format("%s not found with %s : %d",field,fieldId,id));
        this.field = field;
        this.fieldId = fieldId;
        this.id = id;
    }

}
