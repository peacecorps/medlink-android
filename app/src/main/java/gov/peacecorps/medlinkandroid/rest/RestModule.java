package gov.peacecorps.medlinkandroid.rest;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.helpers.HmacSigner;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

@Module
public class RestModule {

    private static final long TIMEOUT_IN_SECONDS = 15;

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    HmacInterceptor provideHmacInterceptor(HmacSigner hmacSigner, DataManager dataManager){
        return new HmacInterceptor(hmacSigner, dataManager);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, HmacInterceptor hmacInterceptor) {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        client.setConnectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        client.interceptors().add(loggingInterceptor);
        client.interceptors().add(hmacInterceptor);

        return client;
    }

    @Provides
    @Singleton
    ConnectionAwareClient provideConnectionAwareClient(Context context, OkHttpClient client) {
        return new ConnectionAwareClient(context, client);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(context.getString(R.string.api_endpoint_base))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    API provideApi(Retrofit retrofit) {
        return retrofit.create(API.class);
    }
}
