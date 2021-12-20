package com.digitallab.academy.shopping.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String numberID;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String status;
    private Region region;
}
