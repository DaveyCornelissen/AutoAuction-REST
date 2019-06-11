package com.dcorn.api.Auction;

import com.dcorn.api.auction.Auction;
import com.dcorn.api.auction.IAuctionRepository;
import com.dcorn.api.bid.Bid;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuctionIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IAuctionRepository auctionRepository;

    private List<Auction> testAuctions;

    private Auction testAuction;

    @Before
    public void Setup() {
        this.testAuctions = new ArrayList<>();
        this.testAuctions.add(new Auction(1, "test", new Date(2019, 8, 21), "Test description", null, BigDecimal.ONE,
                null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

    }

    @Test
    public void mergeNewBid() {
        this.testAuction = this.testAuctions.get(0);

        //create new bid
        Bid newBet = new Bid(1, 1, 1, new Date(), BigDecimal.TEN);
        this.testAuction.setCurrentBid(newBet);

        this.entityManager.merge(newBet);
        this.entityManager.merge(this.testAuction);
        this.entityManager.flush();

        assertNotEquals(0, this.auctionRepository.mergeNewBid(newBet, this.testAuction.getId()));
    }
}
