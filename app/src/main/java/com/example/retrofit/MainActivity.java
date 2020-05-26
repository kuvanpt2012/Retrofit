package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // cach su dung lib retrofit
        // 1: khoi tao ra retrofit

        //OkHttpClient : Config cac connection
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .readTimeout(30, TimeUnit.SECONDS)
                                    .connectTimeout(30, TimeUnit.SECONDS)
                                    .writeTimeout(30, TimeUnit.SECONDS)
                                    .retryOnConnectionFailure(true)
                                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                                    .build();

        //Gson : parse du lieu ve dang object cua java
        Gson gson = new GsonBuilder()
                    .setLenient()
                    .disableHtmlEscaping()
                    .create();

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://khoapham.vn/KhoaPhamTraining/json/tien/")
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

        //2: dinh nghia cac request call HTTP
        //tao interface ApiRequest
        // vao http://www.jsonschema2pojo.org/ setup xong coppy tao demo1

        //3: goi request muon thuc thi

        ApiRequest apiRequest = retrofit.create(ApiRequest.class);

        Call<Demo1> demo1Call = apiRequest.callDemo1();
        demo1Call.enqueue(new Callback<Demo1>() {
            @Override
            public void onResponse(Call<Demo1> call, Response<Demo1> response) {
                Demo1 demo1 = response.body();
                Toast.makeText(MainActivity.this,demo1.getMonhoc(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Demo1> call, Throwable t) {

            }
        });

        //4 : nhan du lieu tu request thong qua phuong thuc call


    }
}
