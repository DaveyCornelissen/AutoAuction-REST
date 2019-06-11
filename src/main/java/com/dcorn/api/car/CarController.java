package com.dcorn.api.car;

import com.dcorn.api.auction.Auction;
import com.dcorn.api.utils.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final ICarService CAR_SERVICE;

    public CarController(ICarService car_service) {
        CAR_SERVICE = car_service;
    }

    @PutMapping("/{id}/update")
    public ResponseHandler update(@NotNull @PathVariable("id") int id, @Valid @RequestBody Car car) {
        car.setId(id);
        CAR_SERVICE.update(car);
        return new ResponseHandler(HttpStatus.OK, "Car updated successful!");
    }

    @GetMapping("/{id}")
    public ResponseHandler read(@NotNull @PathVariable("id") int id){
        return new ResponseHandler(HttpStatus.OK, CAR_SERVICE.read(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseHandler delete(@NotNull @PathVariable("id") int id) {
        //TODO need to get id from the header token to match if auction is the owners

        Car car = new Car();
        car.setId(id);
        CAR_SERVICE.delete(car);
        return new ResponseHandler(HttpStatus.OK, "Auction is successfully deleted");
    }
}
