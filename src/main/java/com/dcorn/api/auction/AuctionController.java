package com.dcorn.api.auction;

import com.dcorn.api.file.IFileService;
import com.dcorn.api.file.Image;
import com.dcorn.api.user.User;
import com.dcorn.api.utils.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {

    private final IAuctionService AUCTION_SERVICE;

    public AuctionController(IAuctionService auction_service) {
        AUCTION_SERVICE = auction_service;
    }

    @PostMapping("/create")
    public ResponseHandler create(@Valid @RequestBody AuctionCreateViewModel auctionCreateViewModel) {
        User seller = new User();
        seller.setId(1);

        Auction auction = new Auction();
        auction.setTitle(auctionCreateViewModel.getTitle());
        auction.setDescription(auctionCreateViewModel.getDescription());
        auction.setDate(auctionCreateViewModel.getDate());
        auction.setPrice(auctionCreateViewModel.getPrice());
        auction.setSeller(seller);
        auction.setCar(auctionCreateViewModel.getCar());

        return new ResponseHandler(HttpStatus.OK, AUCTION_SERVICE.create(auction));
    }

    @PutMapping("/{id}/update")
    public ResponseHandler update(@NotNull @PathVariable("id") int id, @Valid @RequestBody AuctionUpdateViewModel auctionUpdateViewModel) {
        Auction auction = new Auction();
        auction.setId(id);
        auction.setTitle(auctionUpdateViewModel.getTitle());
        auction.setDescription(auctionUpdateViewModel.getDescription());
        auction.setDate(auctionUpdateViewModel.getDate());
        auction.setPrice(auctionUpdateViewModel.getPrice());
        AUCTION_SERVICE.update(auction);
        return new ResponseHandler(HttpStatus.OK, "Auction update successful!");
    }

    @GetMapping("/{id}")
    public ResponseHandler read(@NotNull @PathVariable("id") int id){
        return new ResponseHandler(HttpStatus.OK, AUCTION_SERVICE.read(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseHandler delete(@NotNull @PathVariable("id") int id) {
        //TODO need to get id from the header token to match if auction is the owners

        Auction auction = new Auction();
        auction.setId(id);
        AUCTION_SERVICE.delete(auction);
        return new ResponseHandler(HttpStatus.OK, "Auction is successfully deleted");
    }


}
