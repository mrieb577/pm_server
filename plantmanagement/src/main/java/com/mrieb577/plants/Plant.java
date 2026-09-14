package com.mrieb577.plants;

import java.util.HashMap;

public class Plant extends HashMap<String, String> {
    

    @Override
    public String toString(){
        return get("common_name") + ": " + get("scientific_name");
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof Plant)) return false;
        Plant p = (Plant) o;
        return p.get("scientific_name").equals(get("scientific_name"));
    }
}
