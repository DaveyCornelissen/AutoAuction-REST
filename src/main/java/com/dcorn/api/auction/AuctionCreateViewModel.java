package com.dcorn.api.auction;

import com.dcorn.api.car.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionCreateViewModel {

    @NotNull
    @NotEmpty
    @Size(min=6, max=30, message = "Your title is to short! it needs to be at least 2 characters long!")
    private String title;

    @FutureOrPresent
    private Date date;

    @Size(max = 200, message = "Your description has reached its maximum characters of 200!")
    private String description;

    @NotNull
    private int sellerId;

    @DecimalMin("0.00")
    private BigDecimal price;

    @Valid
    private Car car;
}
