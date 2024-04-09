package com.java.service.adapter_v1;

import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public List<String> getProvinceAPI() throws IOException {
        return thirdPartyAdaptee.requestAPI();
    }
}
