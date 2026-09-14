package com.mrieb577.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrieb577.plants.Plant;
import com.mrieb577.plants.Plants;

public class PlantsDB {
    private static Logger log = LoggerFactory.getLogger(PlantsDB.class);

    private static final String COLUMNS = "plant_id,category,common_name,scientific_name,family,light,watering_interval_days,watering_notes,humidity_preference,min_temp_f,max_temp_f,care_difficulty,toxic_to_pets,toxic_to_humans,mature_size,growth_rate,fertilizer_frequency,fertilizer_type,plant_type,pruning_notes,description";
    private static final String SEARCH_COLUMNS = "category,scientific_name,common_name,family,plant_type";
    private static final String TABLE = "plants";
    
    public static Plants fetch_all(DatabaseConnection conn){
        return search_query(conn, "select " + COLUMNS + " from " + TABLE);
    }
    
    public static Plants search_for_plant(DatabaseConnection conn, String val){
        String[] cols = SEARCH_COLUMNS.split(",");
        String query = "select " + COLUMNS + " from " + TABLE + " where ";
        int index = 0;
        for(String col : cols){
            index++;
            query += col + " like '%" + val + "%' ";
            if(index < cols.length) query += "or ";
        }
        return search_query(conn, query);
    }

    private static Plants search_query(DatabaseConnection conn, String query){
        String[] cols = COLUMNS.split(",");
        Plants result = new Plants();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Plant plant = new Plant();
                for(String col : cols){
                    plant.put(col, resultSet.getString(col));
                }
                result.add(plant);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.error("Unable to execute query - {}", e);
        }
        return result;
    }
}
