package com.mrieb577.responses;

import com.mrieb577.login.UserInfo;

public class LoginRequestResponse extends RequestResponse {
    public String token;
    public String name;
    public String date_joined;

    public LoginRequestResponse(int code, String token, UserInfo user){
        super(code);
        this.token = token;
        if(user != null){
            this.name = user.name;
            this.date_joined = user.date_joined;
        }
    }
}
