package gov.peacecorps.medlinkandroid.rest;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.HmacSigner;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

@Module
public class RestModule {

    private static final long TIMEOUT = 5;

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    HmacInterceptor provideHmacInterceptor(HmacSigner hmacSigner, AppSharedPreferences appSharedPreferences){
        return new HmacInterceptor(hmacSigner, appSharedPreferences);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, HmacInterceptor hmacInterceptor) {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
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
