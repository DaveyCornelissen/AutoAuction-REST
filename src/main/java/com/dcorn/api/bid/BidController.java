package com.dcorn.api.bid;

import com.dcorn.api.utils.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    private final IBidService BID_SERVICE;

    @Autowired
    public BidController(IBidService bid_service) {
        BID_SERVICE = bid_service;
    }

    @PostMapping("/create")
    public ResponseHandler create(@Valid @RequestBody Bid bid) {
        this.BID_SERVICE.create(bid);
        return new ResponseHandler(HttpStatus.OK, "Your bet is added successfully! you're now the highest bidder congratulations!");
    }

    @GetMapping("/{id}")
    public ResponseHandler read(@NotNull @PathVariable("id") int id){
        return new ResponseHandler(HttpStatus.OK, BID_SERVICE.read(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseHandler delete(@NotNull @PathVariable("id") int id) {
        //TODO need to get id from the header token to match if bid is the owners

        Bid bid = new Bid();
        bid.setId(id);
        BID_SERVICE.delete(bid);
        return new ResponseHandler(HttpStatus.OK, "bid is successfully deleted");
    }

}
