package Booking;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class Main {

    private static Dictionary<String, Double> coordinatesPickup = new Hashtable<>();
    private static Dictionary<String, Double> coordinatesDropoff = new Hashtable<>();
    private static Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static int numberPassengers;


    /**
     * @param reqProviderIDs providers IDs for the requested ride
     * @param reqPickupCoord dictionary containing the pickup locations(lat/lon)
     * @param reqDropoffCoord dictionary containing the dropoff locations(lat/lon)
     * @param numberPassengers number of passengers for the requested ride
     * @return return all the cars from a list of providers, the result is sorted by the price of the cars.
     * @throws IOException
     * @throws URISyntaxException
     */
    public static ArrayList<Car> getPricesProvider(ArrayList<String> reqProviderIDs, Dictionary<String, Double> reqPickupCoord,
                                                   Dictionary<String, Double> reqDropoffCoord, int numberPassengers)
                                    throws IOException, URISyntaxException
    {

        HashMap<String, ArrayList<Car>> carsProviders = getCarsProvider(reqProviderIDs, reqPickupCoord,
                                                                        reqDropoffCoord, numberPassengers);
        ArrayList<Car> carPrices = new ArrayList<>();
        if(!carsProviders.isEmpty())
        {
            for(String currentProviderID : reqProviderIDs)
            {
                StringBuilder providerID = new StringBuilder(currentProviderID);
                providerID.deleteCharAt(0);
                currentProviderID = providerID.toString().toUpperCase();
                carPrices = (ArrayList<Car>) carsProviders.get(currentProviderID).stream()
                        .sorted(Comparator.comparing(Car::getPrice).reversed()).collect(Collectors.toList());
                if(!carPrices.isEmpty())
                {
                    return carPrices;
                }
                else
                {
                    LOGGER.info("No cars available for: " + reqProviderIDs);
                }
            }
        }
        return carPrices;
    }

    /**
     * @param reqProviderIDs providers IDs for the requested ride
     * @param reqPickupCoord dictionary containing the pickup locations(lat/lon)
     * @param reqDropoffCoord dictionary containing the dropoff locations(lat/lon)
     * @param numberPassengers number of passengers for the requested ride
     * @return all the cars from a given list of providers that satisfy the number of given passengers
     * @throws IOException
     * @throws URISyntaxException
     */
    public static HashMap<String, ArrayList<Car>> getCarsProvider(ArrayList<String> reqProviderIDs,Dictionary<String, Double> reqPickupCoord,
                                                                  Dictionary<String, Double> reqDropoffCoord, int numberPassengers)
                                                                    throws IOException, URISyntaxException
    {
        HashMap<String, ArrayList<Car>> carsProviders = new HashMap<>();
        ArrayList<Response> responses = new ArrayList<>();
        for(String currentProviderID : reqProviderIDs)
        {
            responses.add(getResponse(currentProviderID, reqPickupCoord, reqDropoffCoord));
        }

        for(Response currentResponse : responses)
        {
            ArrayList<Car> carOptions = new ArrayList<>();
            if(currentResponse != null)
            {
                for(HashMap<String, String> currentOption : currentResponse.getOptions())
                {
                    String carType = currentOption.get("car_type");
                    if(Car_Capacity.valueOfCapacity(Car_Capacity.valueOf(carType)) >= numberPassengers)
                    {
                        Car current_car = new Car(carType, Car_Capacity.valueOfCapacity(Car_Capacity.valueOf(carType)),
                                Integer.valueOf(currentOption.get("price")), currentResponse.getSupplier_id());
                        carOptions.add(current_car);
                    }
                }
                carsProviders.put(currentResponse.getSupplier_id(), carOptions);
            }
        }
        return carsProviders;
    }


    /**
     * @param reqPath provider id
     * @param reqPickupCoord dictionary containing the pickup locations(lat/lon)
     * @param reqDropoffCoord dictionary containing the dropoff locations(lat/lon)
     * @return a JSON containing the response from the provider
     * @throws IOException
     * @throws URISyntaxException
     */
    public static Response getResponse(String reqPath, Dictionary<String, Double> reqPickupCoord,
                                       Dictionary<String, Double> reqDropoffCoord) throws IOException, URISyntaxException
    {
        String uriQuery = "pickup=" + (reqPickupCoord.get("latitude")) + "," + (reqPickupCoord.get("longitude"))
                + "&dropoff=" + (reqDropoffCoord.get("latitude")) + "," + (reqDropoffCoord.get("longitude"));

        URI uri = new URI("https","techtest.rideways.com",reqPath, uriQuery, "");
        URL url = uri.toURL();

        String readLine = null;
        Gson gson = new Gson();
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setConnectTimeout(2000);

        int responseCode = conection.getResponseCode();

        StringBuffer responseLine = new StringBuffer();
        Response response = null;

        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            while ((readLine = in.readLine()) != null)
            {
                System.out.println(readLine);
                responseLine.append(readLine);
            }
            in .close();
            response = gson.fromJson(responseLine.toString(), Response.class);
            return response;
        }
        return response;

    }

    /**
     * @param reqPickupCoord dictionary containing the pickup locations(lat/lon)
     * @param reqDropoffCoord dictionary containing the dropoff locations(lat/lon)
     * @param numberPassengers number of passengers for the requested ride
     * @return return a list of cars(for all available car types)from all providers where
     * each car type has the cheapest provider selected
     * @throws IOException
     * @throws URISyntaxException
     */
    public static ArrayList<Car> getAllCars(Dictionary<String, Double> reqPickupCoord,
                                            Dictionary<String, Double> reqDropoffCoord, int numberPassengers)
                                            throws IOException, URISyntaxException
    {
        ArrayList<Car> carPrices = new ArrayList<>();
        ArrayList<String> providers = new ArrayList<>(Arrays.asList("/dave", "/eric", "/jeff"));
        HashMap<String, ArrayList<Car>> carsProviders = getCarsProvider(providers, reqPickupCoord, reqDropoffCoord, numberPassengers);
        for(Car_Capacity currentCarCapacity : Car_Capacity.values())
        {
            try
            {
                Car found_car = carsProviders.entrySet().stream()
                                .flatMap(s -> s.getValue().stream())
                                .filter(c -> c.getType().equals(currentCarCapacity.name()))
                                .collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Car::getPrice)))).first();
                carPrices.add(found_car);
            }
            catch (NoSuchElementException exception)
            {
                LOGGER.info("No suitable car for " + currentCarCapacity.name());
            }
        }
        return carPrices;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        try
        {
            if(args.length < 4)
                throw new IllegalArgumentException("Please provide enough arguments.");

            double pickupLat = Double.valueOf(args[0]);
            double pickupLon = Double.valueOf(args[1]);
            double dropoffLat = Double.valueOf(args[2]);
            double dropoffLon = Double.valueOf(args[3]);

            coordinatesPickup.put("latitude", pickupLat);
            coordinatesPickup.put("longitude", pickupLon);
            coordinatesDropoff.put("latitude", dropoffLat);
            coordinatesDropoff.put("longitude", dropoffLon);

            if(args.length == 4)
            {
                    numberPassengers = 0;
                    ArrayList<Car> pricesCars = getAllCars(coordinatesPickup, coordinatesDropoff, numberPassengers);
                    for(Car currentCar : pricesCars)
                    {
                        System.out.println(currentCar.getProvider() + " - " + currentCar.getType() + " - " + currentCar.getPrice());
                    }
            }
            else if(args.length == 5)
            {
                    numberPassengers = Integer.parseInt(args[4]);
                    ArrayList<Car> prices_cars = getAllCars(coordinatesPickup, coordinatesDropoff, numberPassengers);
                    for(Car car : prices_cars)
                    {
                        System.out.println(car.getType() + " - " + car.getProvider() + " - " + car.getPrice());
                    }
            }
            else if(args.length == 6)
            {
                if(!args[5].equals("dave") && !args[5].equals("jeff") & !args[5].equals("eric"))
                    throw new InputMismatchException("Please enter a valid name");

                numberPassengers = Integer.parseInt(args[4]);
                ArrayList<String> providerID = new ArrayList<>(Arrays.asList("/" + args[5]));
                ArrayList<Car> carPrices = getPricesProvider(providerID, coordinatesPickup, coordinatesDropoff, numberPassengers);
                if(!carPrices.isEmpty())
                {
                    System.out.println("Available cars from " + args[5]);
                    for(Car car : carPrices)
                    {
                        System.out.println(car.getType() + " - " + car.getPrice());
                    }
                }
                else
                {
                    System.out.println("No available cars from " + args[5]);
                }
            }
        }
        catch(IllegalArgumentException exception)
        {
            LOGGER.info("Exception : " + exception);
        }
        catch(InputMismatchException exception)
        {
            LOGGER.info("Exception: \"" + exception + "\" occured." + "\nPlease enter valid data points.");
        }
        catch(NullPointerException exception)
        {
            LOGGER.info("Exception: \"" + exception + "\" occured.");
        }
    }
}


