package com.techpixe.getphoto.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {

    private String phoneNumber;//destination
    private String userName;
}
