package com.example.petplaystore.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqMobileApplication {

    private Long id;

    @NotBlank
    private Long size;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank
    private Long companyId;

    @NotBlank
    private Long categoryId;

    @NotBlank
    private List<Long> typeId;
}
