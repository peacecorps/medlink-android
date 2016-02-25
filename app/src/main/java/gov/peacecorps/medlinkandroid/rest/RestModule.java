package gov.peacecorps.medlinkandroid.rest;

import android.content.Context;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gov.peacecorps.medlinkandroid.R;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

@Module
public class RestModule {

    private static final long TIMEOUT = 5;

    @Provides
    @Singleton
    Interceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.interceptors().add(loggingInterceptor);

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
