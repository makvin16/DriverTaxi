package zm.com.taxidriver.model.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import zm.com.taxidriver.model.Driver;

public class ResultDriver {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("driver")
    @Expose
    private Driver driver;

    public boolean isSuccess() {
        return success;
    }

    public Driver getDriver() {
        return driver;
    }
}
