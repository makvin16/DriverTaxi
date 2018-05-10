package zm.com.taxidriver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("location_lat")
    @Expose
    private double locationLat;

    @SerializedName("location_lng")
    @Expose
    private double locationLng;

    @SerializedName("animals")
    @Expose
    private boolean animals;

    @SerializedName("drunk")
    @Expose
    private boolean drunk;

    @SerializedName("food")
    @Expose
    private boolean food;

    @SerializedName("smoking")
    @Expose
    private boolean smoking;

    @SerializedName("count_booking")
    @Expose
    private int countBooking;

    @SerializedName("rating")
    @Expose
    private double rating;

    @SerializedName("id_car")
    @Expose
    private int idCar;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public int getStatus() {
        return status;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public boolean isAnimals() {
        return animals;
    }

    public boolean isDrunk() {
        return drunk;
    }

    public boolean isFood() {
        return food;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public int getCountBooking() {
        return countBooking;
    }

    public double getRating() {
        return rating;
    }

    public int getIdCar() {
        return idCar;
    }
}
