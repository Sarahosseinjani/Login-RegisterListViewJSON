package com.example.loginregisterlistviewjson;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users where gmail=(:gmail) and password=(:password) ")
    UserEntity login (String gmail , String password);
}
