package su.rbv.task02_mvp.network;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Web {

    private Retrofit retrofit;

    public Web(String baseURL) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public WebAPI getAPI() {
        return retrofit.create(WebAPI.class);
    }

}
