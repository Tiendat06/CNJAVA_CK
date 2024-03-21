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

    private ThirdPartyAdaptee thirdPartyAdaptee;
    public ProvinceAPIAdapter(){
        this.thirdPartyAdaptee = new ThirdPartyAdaptee();
    }

    public ProvinceAPIAdapter(ThirdPartyAdaptee thirdPartyAdaptee){
        this.thirdPartyAdaptee = thirdPartyAdaptee;
    }
    @Override
    public List<String> getProvinceAPI() {
        List<String> list = new ArrayList<>();

        try {
            Response response = thirdPartyAdaptee.requestAPI();
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
