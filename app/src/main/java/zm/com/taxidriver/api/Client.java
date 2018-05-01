package zm.com.taxidriver.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zm.com.taxidriver.General;

public class Client {
    private static final String URL = General.SERVER;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
