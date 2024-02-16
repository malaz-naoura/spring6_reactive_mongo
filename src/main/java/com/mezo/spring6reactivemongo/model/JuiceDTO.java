package com.mezo.spring6reactivemongo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JuiceDTO {

    private String id;
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    @Size(min = 1, max = 255)
    private String style;
    @Size(max = 25)
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}