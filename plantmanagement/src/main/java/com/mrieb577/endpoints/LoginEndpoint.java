package com.mrieb577.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.UsersDB;
import com.mrieb577.login.AuthRequest;
import com.mrieb577.login.JwtUtil;
import com.mrieb577.login.NewUserInfo;
import com.mrieb577.login.UserInfo;
import com.mrieb577.login.UserInfoService;
import com.mrieb577.responses.LoginRequestResponse;
import com.mrieb577.responses.RequestResponse;

@RestController
@RequestMapping("/account")
public class LoginEndpoint {
    private static Logger log = LoggerFactory.getLogger(LoginEndpoint.class);

    private final UserInfoService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginEndpoint(UserInfoService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody NewUserInfo userInfo){
        Gson gson = new Gson();
        DatabaseConnection connection = new DatabaseConnection();
        if (!connection.isConnected()) {
            return gson.toJson(new RequestResponse(RequestResponse.SERVER_ERROR_CODE, "Server error"));
        }
        // check that the email does not exist in the database
        UserInfo user = UsersDB.getUserByEmail(connection, userInfo.getEmail());
        log.info("found user id - {}", user.user_id);
        if(user.email == null){
            UserInfo newUser = new UserInfo();
            newUser.name = userInfo.getName();
            newUser.email = userInfo.getEmail();
            newUser.password = userInfo.getPassword();
            return userDetailsService.addUser(newUser);
        }
        else return gson.toJson(new RequestResponse(RequestResponse.REDUNDANT_SUCCESS_CODE, "User already exists"));
    }

    @PostMapping("/generate-token") // aka login
    public String generateToken(@RequestBody AuthRequest authRequest){
        Gson gson = new Gson();
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password) 
            );
            if(authentication.isAuthenticated()){
                DatabaseConnection connection = new DatabaseConnection();
                if (!connection.isConnected()) {
                    return gson.toJson(new RequestResponse(RequestResponse.SERVER_ERROR_CODE, "Server error"));
                }
                UserInfo user = UsersDB.getUserByEmail(connection, authRequest.username);
                String token = jwtUtil.generateToken(authRequest.username);
                return gson.toJson(new LoginRequestResponse(RequestResponse.SUCCESS_CODE, token, user));
            }
            return gson.toJson(new RequestResponse(RequestResponse.ACCESS_DENIED_CODE, "Access denied"));
        } catch (Exception e){
            return gson.toJson(new RequestResponse(RequestResponse.SERVER_ERROR_CODE, "Server error"));
        }
    }
}
