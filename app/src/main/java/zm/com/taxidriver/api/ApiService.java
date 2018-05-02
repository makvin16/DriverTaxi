package zm.com.taxidriver.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import zm.com.taxidriver.model.PhoneEmail;
import zm.com.taxidriver.model.ResultColor;
import zm.com.taxidriver.model.ResultType;

public interface ApiService {

    @FormUrlEncoded
    @POST("/driver/check_phone_email.php")
    Call<PhoneEmail> apiCheckPhoneEmail(
            @Field("phone") String phone,
            @Field("email") String email
    );

    @GET("/driver/get_types.php")
    Call<ResultType> apiGetTypes();

    @GET("/driver/get_colors.php")
    Call<ResultColor> apiGetColors();
}
