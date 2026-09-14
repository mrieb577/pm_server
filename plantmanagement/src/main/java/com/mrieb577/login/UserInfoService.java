package com.mrieb577.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.UsersDB;

@Service
public class UserInfoService implements UserDetailsService {

    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DatabaseConnection connection = new DatabaseConnection();
        UserInfo user = UsersDB.getUserByEmail(connection, username);
        return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
    
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        DatabaseConnection dbConnection = new DatabaseConnection();
        UsersDB.addUser(dbConnection, userInfo);
        return "Added successfully";
    }
}
