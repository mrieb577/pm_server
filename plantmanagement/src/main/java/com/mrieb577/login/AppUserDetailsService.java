package com.mrieb577.login;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.UsersDB;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // use your existing DatabaseConnection / Credentials here
        // query users table
        // return User.withUsername(...).password(...).authorities(...).build();
        DatabaseConnection dbConnection = new DatabaseConnection();
        UserInfo userInfo = UsersDB.getUserByEmail(dbConnection, username);
        dbConnection.close();
        if (userInfo.email == null) {
            throw new RuntimeException("User not found");
        }
        return User.withUsername(userInfo.email)
                .password(userInfo.password)
                .authorities(userInfo.roles.split(","))
                .build();
    }
}
