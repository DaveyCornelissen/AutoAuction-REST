package com.dcorn.api.bid;

import com.dcorn.api.auction.Auction;
import com.dcorn.api.auction.IAuctionService;
import com.dcorn.api.utils.handler.BadRequestHandler;
import com.dcorn.api.utils.handler.InternalServerErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class BidService implements IBidService {

    private final IBidRepository BID_REPOSITORY;
    private final IAuctionService AUCTION_SERVICE;

    @Autowired
    public BidService(IBidRepository bid_repository, IAuctionService auction_service) {
        this.BID_REPOSITORY = bid_repository;
        this.AUCTION_SERVICE = auction_service;
    }

    @Override
    public Bid create(Bid object) {
        Auction currentAuction = AUCTION_SERVICE.read(object.getAuctionId());

        Bid currentBid = currentAuction.getCurrentBid();

        if (currentBid != null) {
            if (currentBid.getPrice().compareTo(object.getPrice()) >= 0)
                throw new BadRequestHandler("Price is to low! it needs to be higher then the current Price!");

            LocalDateTime serverTime = LocalDateTime.now();
            LocalDateTime highestBidTime =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(currentBid.getTime().toString())), ZoneId.systemDefault());

            if (highestBidTime.compareTo(serverTime) == -1)
                throw new BadRequestHandler("Looks like someone was faster with bidding! please retry with an new price value");
        } else {
            if (currentAuction.getPrice().compareTo(object.getPrice()) == 1)
                throw new BadRequestHandler("your current bid price is to low!");
        }

        Bid newBid = BID_REPOSITORY.save(object);

        if (newBid == null)
            throw new InternalServerErrorHandler("Oops something went wrong saving the Bid object!");

        //Add's the new bidding to the current Auction
        AUCTION_SERVICE.mergeNewBet(currentAuction.getId(), newBid);

        return object;
    }

    @Override
    public Bid read(Integer id) {
        if (id == 0)
            throw new BadRequestHandler("No valid bid id provided!");

        return BID_REPOSITORY.findById(id).orElseThrow(() -> new BadRequestHandler("No bidding found with id:" + id));
    }

    @Override
    public void update(Bid object) {
        //Cant update a bidding
    }

    @Override
    public void delete(Bid object) {
        Bid removeObject = BID_REPOSITORY.findById(object.getId()).orElseThrow(() ->
                new BadRequestHandler("No bidding was found with that id!"));

        BID_REPOSITORY.delete(removeObject);
    }
}
