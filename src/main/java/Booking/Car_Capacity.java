package Booking;

import java.util.HashMap;
import java.util.Map;

public enum Car_Capacity {

    STANDARD(4),
    EXECUTIVE(4),
    LUXURY(4),
    PEOPLE_CARRIER(6),
    LUXURY_PEOPLE_CARRIER(6),
    MINIBUS(16);

    private static final Map<Car_Capacity, Integer> BY_CAPACITY = new HashMap<>();
    public final Integer capacity;

    static {
        for (Car_Capacity e : values()) {
            BY_CAPACITY.put(e, e.capacity);
        }
    }

    Car_Capacity(Integer capacity) {
        this.capacity = capacity;
    }

    public static int valueOfCapacity(Car_Capacity number) {
        return BY_CAPACITY.get(number);
    }
}
