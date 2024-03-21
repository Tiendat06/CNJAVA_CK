package com.java.service.adapter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyAdaptee {
    public Response requestAPI() throws IOException {
        String APIUrl = "https://api.ghsv.vn/v1/address/provinces";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIUrl)
                .build();

        return okHttpClient.newCall(request).execute();
    }
}
