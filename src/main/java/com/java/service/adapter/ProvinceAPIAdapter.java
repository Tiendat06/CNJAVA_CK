package com.java.service.adapter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceAPIAdapter implements IProvinceAPI{

    @Override
    public List<String> getProvinceAPI() {
        List<String> list = new ArrayList<>();
        String APIUrl = "https://api.ghsv.vn/v1/address/provinces";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIUrl)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray data = jsonObject.getJSONArray("provinces");
            for (int i = 0; i < data.length(); i++) {
                String name = data.getJSONObject(i).getString("name");
                list.add(name);
            }

        }catch (IOException | JSONException ex){
            ex.printStackTrace();
        }
        return list;
    }
}
