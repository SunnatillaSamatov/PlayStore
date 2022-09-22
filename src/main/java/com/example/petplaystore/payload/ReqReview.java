package com.example.petplaystore.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqReview {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Integer rating;

    private String review;

    private Long mobileApplicationId;


}
