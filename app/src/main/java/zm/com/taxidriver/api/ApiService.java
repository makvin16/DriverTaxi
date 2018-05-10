package zm.com.taxidriver.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import zm.com.taxidriver.model.result.PhoneEmail;
import zm.com.taxidriver.model.result.ResultCar;
import zm.com.taxidriver.model.result.ResultDriver;
import zm.com.taxidriver.model.result.ResultType;

public interface ApiService {

    @FormUrlEncoded
    @POST("/driver/check_phone_email.php")
    Call<PhoneEmail> apiCheckPhoneEmail(
            @Field("phone") String phone,
            @Field("email") String email
    );

    @GET("/driver/get_types.php")
    Call<ResultType> apiGetTypes();

    @FormUrlEncoded
    @POST("/driver/set_car.php")
    Call<ResultCar> apiSetCar(
            @Field("id_type") int id_type,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("year") int year,
            @Field("color") String color,
            @Field("number") String number,
            @Field("count_passanger") int pas
    );

    @FormUrlEncoded
    @POST("/driver/set.php")
    Call<ResultDriver> apiSetDriver(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("photo") String photo,
            @Field("animals") boolean animals,
            @Field("drunk") boolean drunk,
            @Field("food") boolean food,
            @Field("smoking") boolean smoking,
            @Field("id_car") int id_car
    );

    @GET("/driver/get_car_by_number.php")
    Call<ResultCar> apiGetCarByNumber(
            @Query("number") String number
    );

    @FormUrlEncoded
    @POST("/driver/signin.php")
    Call<ResultDriver> apiSignInDriver(
            @Field("phone") String phone,
            @Field("password") String password
    );


}
