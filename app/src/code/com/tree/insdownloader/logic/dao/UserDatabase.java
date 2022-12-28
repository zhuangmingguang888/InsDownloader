package com.tree.insdownloader.logic.dao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.tree.insdownloader.app.App;
import com.tree.insdownloader.logic.model.User;

@Database(version = 1, entities = User.class,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static String DB_NAME = "user_database";
    public static UserDatabase instance;

    public abstract UserDao userDao();

    public static UserDatabase getInstance() {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(App.getAppContext(), UserDatabase.class, DB_NAME).build();
                }
            }
        }
        return instance;
    }
}
