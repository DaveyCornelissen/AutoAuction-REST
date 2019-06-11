package com.dcorn.api.auction;

import com.dcorn.api.bid.Bid;
import com.dcorn.api.car.Car;
import com.dcorn.api.car.ICarService;
import com.dcorn.api.file.Image;
import com.dcorn.api.user.IUserService;
import com.dcorn.api.user.User;
import com.dcorn.api.utils.handler.BadRequestHandler;
import com.dcorn.api.utils.handler.InternalServerErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService implements IAuctionService {

    private final IAuctionRepository AUCTION_REPOSITORY;
    private final ICarService CAR_SERVICE;
    private final IUserService USER_SERVICE;

    @Autowired
    public AuctionService(IAuctionRepository auction_repository, ICarService car_service, IUserService user_service) {
        AUCTION_REPOSITORY = auction_repository;
        CAR_SERVICE = car_service;
        USER_SERVICE = user_service;
    }

    @Override
    public void mergeNewBet(int id, Bid newBet) {
        if (id == 0 || newBet == null)
            throw new BadRequestHandler("No valid auction id or bid object provided!");

        if (AUCTION_REPOSITORY.mergeNewBid(newBet, id) == 0)
            throw new InternalServerErrorHandler("Oops something went wrong merging the data to the database!");
    }

    @Override
    public void mergeNewImages(int id, List<Image> imageList) {
        if (id == 0 || imageList == null)
            throw new BadRequestHandler("No valid auction id or Image list provided!");

        Auction auction = read(id);
        auction.setImages(imageList);
        update(auction);
    }

    @Override
    public Auction create(Auction object) {
        if (AUCTION_REPOSITORY.findById(object.getId()).isPresent())
            throw new BadRequestHandler("This id already exist!");

        Car car = CAR_SERVICE.create(object.getCar());
        if (car == null)
            throw new InternalServerErrorHandler("Oops something went wrong! could not create/save car object!");

        User seller = USER_SERVICE.read(object.getSeller().getId());
        if (seller == null)
            throw new InternalServerErrorHandler("Oops something went wrong! could not read/get user object!");

        object.setSeller(seller);
        object.setCar(car);
        Auction newAuction = AUCTION_REPOSITORY.save(object);

        if (newAuction == null)
            throw new InternalServerErrorHandler("Oops something went wrong! could not create auction object!");

        return newAuction;
    }

    @Override
    public Auction read(Integer id) {
        if (id == 0)
            throw new BadRequestHandler("No valid auction id provided!");

        return AUCTION_REPOSITORY.findById(id).orElseThrow(() -> new BadRequestHandler("No auction found with id:" + id));
    }

    @Override
    public void update(Auction object) {
        if (AUCTION_REPOSITORY.save(object) == null)
            throw new InternalServerErrorHandler("Something went wrong with updating the Auction!");
    }

    @Override
    public void delete(Auction object) {

        //TODO check if the id of the owner match the auction

        Auction removeObject = AUCTION_REPOSITORY.findById(object.getId()).orElseThrow(() ->
                new BadRequestHandler("No auction was found with that id!"));

        AUCTION_REPOSITORY.delete(removeObject);
    }
}
