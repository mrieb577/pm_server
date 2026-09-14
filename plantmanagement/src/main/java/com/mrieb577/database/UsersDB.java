package com.mrieb577.database;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrieb577.login.UserInfo;

public class UsersDB {
    private static Logger log = LoggerFactory.getLogger(UsersDB.class);

    private static final String COLUMNS = "user_id,name,email,password,date_joined,roles";
    private static final String INSERT_COLUMNS = "name,email,password,date_joined,roles";
    private static final String TABLE = "userdata";

    public static UserInfo getUserByEmail(DatabaseConnection conn, String email){
        String query = "select " + COLUMNS + " from " + TABLE + " where email = '" + email + "'";
        return query(conn, query);
    }

    public static void addUser(DatabaseConnection dbConnection, UserInfo userInfo) {
        String query = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ('" +
            userInfo.name + "', '" +
            userInfo.email + "', '" +
            userInfo.password + "', '" +
            java.sql.Date.valueOf(LocalDate.now()) + "', '" +
            userInfo.roles + "')";
        try {
            log.info(query);
            var statement = dbConnection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
            log.error("Unable to execute query - {}", e);
        }
    }

    private static UserInfo query(DatabaseConnection conn, String query){
        UserInfo result = new UserInfo();
        try {
            var statement = conn.createStatement();
            var resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                result.user_id = resultSet.getLong("user_id");
                result.name = resultSet.getString("name");
                result.email = resultSet.getString("email");
                result.password = resultSet.getString("password");
                result.date_joined = resultSet.getString("date_joined");
                result.roles = resultSet.getString("roles");
            }
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            log.error("Unable to execute query - {}", e);
        }
        return result;
    }
}
