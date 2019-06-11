package com.dcorn.api.auction;

import com.dcorn.api.bid.Bid;
import com.dcorn.api.car.Car;
import com.dcorn.api.comment.Comment;
import com.dcorn.api.file.Image;
import com.dcorn.api.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private Date date;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private User seller;

    private BigDecimal price;

    @OneToOne(fetch = FetchType.EAGER)
    private Car car;

    @OneToOne(fetch = FetchType.EAGER)
    private Bid currentBid;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<User> visitors;

    @OneToMany
    private List<Bid> bidding;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Image> images;

}
