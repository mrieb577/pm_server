package com.mrieb577.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrieb577.login.UserInfo;
import com.mrieb577.plants.UserPlant;
import com.mrieb577.plants.UserPlants;

public class UserPlantsDB {
    private static Logger log = LoggerFactory.getLogger(UserPlantsDB.class);

    private static final String TABLE = "userplants";
    private static final String ADD_COLUMNS = "nickname,notes,is_indoor,water_interval,water_quantity_ml,last_watered,fertilize_interval,last_fertilized,date_acquired,location,user_id,plant_id";

    public static String add(DatabaseConnection conn, UserPlant plant, UserInfo user){
        log.info("Adding plant {} for user {}", plant.plant_id, user.user_id);
        String query = "insert into " + TABLE + " (" + ADD_COLUMNS + ") values (\"" +
            plant.nickname + "\", \"" +
            plant.notes + "\", " +
            plant.isIndoor + ", " +
            plant.waterInterval + ", " +
            plant.waterAmount + ", \"" +
            plant.lastWatered + "\", " +
            plant.fertilizerInterval + ", \"" +
            plant.lastFertilized + "\", \"" +
            plant.dateAcquired + "\", \"" +
            plant.location + "\", " +
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

    public static UserPlants getUserPlants(DatabaseConnection conn, UserInfo user){
        String query = "select * from " + TABLE + " where user_id = " + user.user_id + ";";
        log.info("Getting plants for user {}; {}", user.user_id, query);
        try {
            var statement = conn.createStatement();
            var resultSet = statement.executeQuery(query);
            UserPlants userPlants = new UserPlants();
            while(resultSet.next()){
                UserPlant plant = new UserPlant();
                plant.user_plant_id = resultSet.getLong("user_plant_id");
                plant.nickname = resultSet.getString("nickname");
                plant.notes = resultSet.getString("notes");
                plant.isIndoor = resultSet.getBoolean("is_indoor");
                plant.waterInterval = resultSet.getInt("water_interval");
                plant.waterAmount = resultSet.getInt("water_quantity_ml");
                plant.lastWatered = resultSet.getString("last_watered");
                plant.fertilizerInterval = resultSet.getInt("fertilize_interval");
                plant.lastFertilized = resultSet.getString("last_fertilized");
                plant.dateAcquired = resultSet.getString("date_acquired");
                plant.location = resultSet.getString("location");
                plant.user_id = resultSet.getInt("user_id");
                plant.plant_id = resultSet.getInt("plant_id");
                userPlants.add(plant);
            }
            statement.close();
            return userPlants;
        } catch (Exception e){
            log.error("Unable to execute query - {}", e);
            return new UserPlants();
        }
    }
}
