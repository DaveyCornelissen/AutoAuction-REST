package com.dcorn.api.bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    private Account account;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    private Auction auction;

    @NotNull
    private int accountId;

    @NotNull
    private int auctionId;

    @PastOrPresent
    private Date time;

    @DecimalMin("0.00")
    private BigDecimal price;

}
