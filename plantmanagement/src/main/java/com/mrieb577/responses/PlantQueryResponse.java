package com.mrieb577.responses;

import com.mrieb577.plants.Plants;

public class PlantQueryResponse extends RequestResponse {
    public Plants results;
    public int count;

    public PlantQueryResponse(int code, Plants res){
        super(code, "OK");
        results = res;
        count = res.size();
    }

    public PlantQueryResponse(int code, String message){
        super(code, message);
    }
}
