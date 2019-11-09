package Booking;

import java.util.ArrayList;
import java.util.HashMap;

public class Response {

    private String supplier_id;
    private String pickup;
    private String dropoff;
    private ArrayList<HashMap<String, String>> options;


    public Response(String supplier_id, String pickup, String dropoff, ArrayList<HashMap<String, String>> options) {
        this.supplier_id = supplier_id;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.options = options;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public ArrayList<HashMap<String, String>> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<HashMap<String, String>> options) {
        this.options = options;
    }
}
