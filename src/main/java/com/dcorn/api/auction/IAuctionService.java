package com.dcorn.api.auction;

import com.dcorn.api.bid.Bid;
import com.dcorn.api.file.Image;
import com.dcorn.api.utils.CrudOperation;

import java.util.List;

public interface IAuctionService extends CrudOperation<Auction> {

    void mergeNewBet(int id, Bid newBet);

    void mergeNewImages(int id, List<Image> imageList);
}
