package com.dcorn.api.auction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionUpdateViewModel {

    @NotNull
    @NotEmpty
    @Size(min=2, message = "Your title is to short! it needs to be at least 2 characters long!")
    private String title;

    @FutureOrPresent
    private Date date;

    @Size(max = 200, message = "Your description has reached its maximum characters of 200!")
    private String description;

    @DecimalMin("0.00")
    private BigDecimal price;
}
