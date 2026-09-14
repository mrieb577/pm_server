package com.mrieb577.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrieb577.login.UserInfo;
import com.mrieb577.plants.UserPlant;

public class UserPlantsDB {
    private static Logger log = LoggerFactory.getLogger(UserPlantsDB.class);

    private static final String TABLE = "userplants";
    private static final String ADD_COLUMNS = "nickname,notes,is_indoor,water_interval,water_quantity_ml,last_watered,fertilize_interval,last_fertilized,date_acquired,location,user_id,plant_id";

    public static String add(DatabaseConnection conn, UserPlant plant, UserInfo user){
        log.info("Adding plant {} for user {}", plant.plant_id, user.user_id);
        String query = "insert into " + TABLE + " (" + ADD_COLUMNS + ") values (" +
            plant.nickname + ", " +
            plant.notes + ", " +
            plant.isIndoor + ", " +
            plant.waterInterval + ", " +
            plant.waterAmount + ", " +
            plant.lastWatered + ", " +
            plant.fertilizerInterval + ", " +
            plant.lastFertilized + ", " +
            plant.dateAcquired + ", " +
            plant.location + ", " +
            user.user_id + ", " +
            plant.plant_id + ");";
            
        if(add_query(conn, query))
            return "Success for user " + user.email;
        else return "Unable to add plant to user " + user.email;
    }
        
    private static boolean add_query(DatabaseConnection conn, String query){
        try {
            log.info(query);
            var statement = conn.createStatement();
            statement.executeUpdate(query);
            statement.close();
            return true;
        } catch (Exception e){
            log.error("Unable to execute query - {}", e);
            return false;
        }
    }
}
