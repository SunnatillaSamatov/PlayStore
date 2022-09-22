package com.example.petplaystore.payload;

import com.example.petplaystore.entity.MobileApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserAccount {
    private String username;
    private Long mobileApplicationId;
}