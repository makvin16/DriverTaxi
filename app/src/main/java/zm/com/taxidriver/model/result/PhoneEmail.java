package zm.com.taxidriver.model.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneEmail {

    @SerializedName("phone")
    @Expose
    private boolean isPhone;

    @SerializedName("email")
    @Expose
    private boolean isEmail;

    public void setPhone(boolean phone) {
        isPhone = phone;
    }

    public void setEmail(boolean email) {
        isEmail = email;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public boolean isEmail() {
        return isEmail;
    }
}
