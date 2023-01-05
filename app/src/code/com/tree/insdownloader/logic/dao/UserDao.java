package com.tree.insdownloader.logic.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tree.insdownloader.logic.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void UpdateUser(User user);

    @Query("select * from User")
    List<User> getAllUser();

    @Query("select * from User where fileName = :fileName")
    User getUserByFileName(String fileName);

}
