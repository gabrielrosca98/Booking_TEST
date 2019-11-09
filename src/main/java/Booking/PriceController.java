package Booking;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

@RestController
public class PriceController {

    @RequestMapping("/price")
    public Price price(@RequestParam(value="pickup")String pickup, @RequestParam(value="dropoff") String dropoff,
                       @RequestParam(value="numberpassengers", defaultValue = "1") int numberPassengers,
                       @RequestParam(value="provider_id", defaultValue = "") String providerID ) throws IOException, URISyntaxException
    {
        Dictionary<String, Double> coordinatesPickup = new Hashtable<>();
        Dictionary<String, Double> coordinatesDropoff = new Hashtable<>();

        double pickupLat = Double.valueOf(pickup.substring(0, pickup.indexOf(',')));
        double pickupLon = Double.valueOf(pickup.substring(pickup.indexOf(',') + 1));
        double dropoffLat = Double.valueOf(dropoff.substring(0, pickup.indexOf(',')));
        double dropoffLon = Double.valueOf(dropoff.substring(pickup.indexOf(',') + 1));

        coordinatesPickup.put("latitude", pickupLat);
        coordinatesPickup.put("longitude", pickupLon);
        coordinatesDropoff.put("latitude", dropoffLat);
        coordinatesDropoff.put("longitude", dropoffLon);

        if(providerID.equals(""))
        {
            return new Price(Main.getAllCars(coordinatesPickup, coordinatesDropoff, Integer.valueOf(numberPassengers)));
        }
        else
        {
            ArrayList<String> provider_id = new ArrayList<>(Arrays.asList("/" + providerID));
            return new Price(Main.getPricesProvider(provider_id, coordinatesPickup, coordinatesDropoff, numberPassengers));
        }
    }


}
