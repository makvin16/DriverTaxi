package zm.com.taxidriver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Car implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_type")
    @Expose
    private int idType;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("count_passanger")
    @Expose
    private int countPassanger;

    public int getId() {
        return id;
    }

    public int getIdType() {
        return idType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCountPassanger() {
        return countPassanger;
    }
}
