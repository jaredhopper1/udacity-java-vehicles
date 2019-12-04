package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.util.*;
import java.util.Optional;

import org.springframework.stereotype.Service;


/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final PriceClient priceClient;
    private final MapsClient mapsClient;


    public CarService(CarRepository repository, PriceClient priceClient, MapsClient mapsClient) {
        /**
         * Completed: Add the Maps and Pricing Web Clients you create
         *   in `VehiclesApiApplication` as arguments and set them here.
         */
        this.repository = repository;
        this.priceClient = priceClient;
        this.mapsClient = mapsClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        List<Car> allCars = new ArrayList<>();
        int allCarsSize = repository.findAll().size();

        for(int i = 1;i <=allCarsSize; i++) {
            // Finding each car and adding price and location client to each.
            Car car = findCarByIdList((long) i);
            allCars.add(car);
        }
        return repository.findAll();
    }

    public Car findCarByIdList(Long id) {
        Car car = new Car();
        Optional<Car> optionalCar = repository.findById(id);
        if(optionalCar.isPresent()) {
            car = optionalCar.get();
                // Setiing Price for each car id.
            String newPrice = priceClient.getPrice(id);
            car.setPrice(newPrice);
                // Setiing location for each car id.
            Location location = car.getLocation();
            car.setLocation(mapsClient.getAddress(location));
        }
        return car;
    }


    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /**
         * Completed: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *   Remove the below code as part of your implementation.
         */
        Car car;
        Optional<Car> optionalCar = repository.findById(id);
        car = optionalCar.orElseThrow(CarNotFoundException::new);


        /**
         * Completed: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input'
         * Completed: Set the price of the car
         * Note: The car class file uses @transient, meaning you will need to call
         *   the pricing service each time to get the price.
         */
        String newPrice = priceClient.getPrice(id);
        car.setPrice(newPrice);

        /**
         * Completed: Use the Maps Web client you create in `VehiclesApiApplication`
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service.
         * Completed: Set the location of the vehicle, including the address information
         * Note: The Location class file also uses @transient for the address,
         * meaning the Maps service needs to be called each time for the address.
         */
        Location location = car.getLocation();
        car.setLocation(mapsClient.getAddress(location));

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     * Default save method modified to get update.
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        // Setting car condition on Update/ Setting updated car condition.
                        carToBeUpdated.setCondition(car.getCondition());
                        // Resetting price on update.
                        carToBeUpdated.setPrice(priceClient.getPrice(car.getId()));
                        carToBeUpdated.setLocation(mapsClient.getAddress(car.getLocation()));
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
//        If car not found create new car --- and save.
//        Price client needs Id to generate price for vehicle, Id will be generated after vehicle is create  then
//        how we get the price here. -- Need to think.
        car.setPrice("42000");
        car.setLocation(mapsClient.getAddress(car.getLocation()));
        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /**
         * Completed: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */
        Car car;
        Optional<Car> optionalCar = repository.findById(id);
        car = optionalCar.orElseThrow(CarNotFoundException::new);

        /**
         * Completed: Delete the car from the repository.
         */
        if(car != null) {
            repository.delete(car);
        }

    }
}