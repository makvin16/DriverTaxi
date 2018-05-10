package zm.com.taxidriver.model.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import zm.com.taxidriver.model.Car;

public class ResultCar {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("car")
    @Expose
    private Car car;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
         return error;
    }

    public Car getCar() {
        return car;
    }
}
