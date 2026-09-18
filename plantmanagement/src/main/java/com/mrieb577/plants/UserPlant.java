package com.mrieb577.plants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPlant {
    private static final DateTimeFormatter SQL_DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long user_plant_id;
    public String nickname;
    public String notes;
    public Boolean isIndoor;
    public Integer waterInterval;
    public Integer waterAmount;
    public String lastWatered;
    public Integer fertilizerInterval;
    public String lastFertilized;
    public String dateAcquired;
    public String location;
    public Integer user_id;
    public Integer plant_id;

    public static UserPlant verifyPlant(UserPlant plant){
        if(plant.nickname == null) plant.nickname = "Unknown";
        if(plant.notes == null) plant.notes = "";
        if(plant.isIndoor == null) plant.isIndoor = false;
        if(plant.waterInterval == null) plant.waterInterval = 7;
        if(plant.waterAmount == null) plant.waterAmount = 100;
        if(plant.lastWatered == null) plant.lastWatered = LocalDateTime.now().format(SQL_DATE_TIME_FORMATTER);
        if(plant.fertilizerInterval == null) plant.fertilizerInterval = 30;
        if(plant.lastFertilized == null) plant.lastFertilized = LocalDateTime.now().format(SQL_DATE_TIME_FORMATTER);
        if(plant.dateAcquired == null) plant.dateAcquired = LocalDateTime.now().format(SQL_DATE_TIME_FORMATTER);
        if(plant.location == null) plant.location = "Unknown";
        return plant;
    }
}
