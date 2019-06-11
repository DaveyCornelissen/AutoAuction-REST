package com.dcorn.api.auction;

import com.dcorn.api.bid.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IAuctionRepository extends JpaRepository<Auction, Integer> {

    @Modifying
    @Query("update Auction a set a.currentBid = ?1 where a.id = ?2")
    int mergeNewBid(Bid newBet, int id);
}
