package com.dcorn.api.car;

import com.dcorn.api.utils.handler.BadRequestHandler;
import com.dcorn.api.utils.handler.InternalServerErrorHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService implements ICarService {

    private final ICarRepository CAR_REPOSITORY;

    public CarService(ICarRepository carRepository) {
        this.CAR_REPOSITORY = carRepository;
    }

    @Override
    public Car create(Car object) {

        Optional test = CAR_REPOSITORY.findById(object.getId());

        if (test.isPresent())
            throw new BadRequestHandler("This id already exist!");

        Car newCar = CAR_REPOSITORY.save(object);

        if (newCar == null)
            throw new InternalServerErrorHandler("Oops something went wrong! could not create object!");

        return newCar;
    }

    @Override
    public Car read(Integer id) {
        if (id == 0)
            throw new BadRequestHandler("No valid auction id provided!");

        return CAR_REPOSITORY.findById(id).orElseThrow(() -> new BadRequestHandler("No car found with id:" + id));
    }

    @Override
    public void update(Car object) {
        if (CAR_REPOSITORY.save(object) == null)
            throw new InternalServerErrorHandler("Something went wrong with updating the Car!");
    }

    @Override
    public void delete(Car object) {
        Car removeObject = CAR_REPOSITORY.findById(object.getId()).orElseThrow(() ->
                new BadRequestHandler("No car was found with that id!"));

        CAR_REPOSITORY.delete(removeObject);
    }
}
