package com.example.employeeservice.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    String errorMessage;
    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
