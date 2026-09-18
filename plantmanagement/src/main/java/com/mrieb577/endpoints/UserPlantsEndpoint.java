package com.mrieb577.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.UserPlantsDB;
import com.mrieb577.database.UsersDB;
import com.mrieb577.login.UserInfo;
import com.mrieb577.plants.UserPlant;
import com.mrieb577.plants.UserPlants;
import com.mrieb577.responses.RequestResponse;

@RestController
@RequestMapping("/user/plants")
public class UserPlantsEndpoint {
    private static Logger log = LoggerFactory.getLogger(UserPlantsEndpoint.class);

    @GetMapping("/get")
    public String getUserPlants(Authentication authentication){
        Gson gson = new Gson();
        DatabaseConnection db = new DatabaseConnection();
        if (!db.isConnected()) {
            return gson.toJson(new RequestResponse(RequestResponse.SERVER_ERROR_CODE, "Failed to connect to database"));
        }
        log.info("authentication - {}", authentication.toString());
        UserInfo currentUser = UsersDB.getUserByEmail(db, authentication.getName());
        try {
            UserPlants userPlants = UserPlantsDB.getUserPlants(db, currentUser);
            log.info("User {} has {} plants", currentUser.user_id, userPlants.size());
            return gson.toJson(userPlants);
        } finally {
            db.close();
        }
    }

    @PostMapping("/add")
    public String addPlant(@RequestBody UserPlant plant, Authentication authentication){
        Gson gson = new Gson();
        log.info("Adding plant {} for user {}", gson.toJson(plant), authentication.getName());
        DatabaseConnection db = new DatabaseConnection();
        if (!db.isConnected()) {
            return gson.toJson(new RequestResponse(RequestResponse.SERVER_ERROR_CODE, "Failed to connect to database"));
        }
        log.info("authentication - {}", authentication.toString());
        UserInfo currentUser = UsersDB.getUserByEmail(db, authentication.getName());
        try {
            UserPlant verifiedPlant = UserPlant.verifyPlant(plant);
            log.info("Verified plant: {}", gson.toJson(verifiedPlant));
            return UserPlantsDB.add(db, verifiedPlant, currentUser);
        } finally {
            db.close();
        }
    }
}
