package com.wut.money.transfer.app.dto;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerLogin {

    private int customerId;
    private String customerPassword;
    private String firstName;
    private String lastName;
}
