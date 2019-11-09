package Booking;

import java.util.ArrayList;

public class Price {

    private static ArrayList<Car> cars;

    public Price(ArrayList<Car> cars)
    {
        this.cars = cars;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        cars = cars;
    }
}
