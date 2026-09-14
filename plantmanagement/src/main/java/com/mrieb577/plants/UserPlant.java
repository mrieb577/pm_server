package com.mrieb577.plants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long user_plant_id;
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
}
