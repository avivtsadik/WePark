package room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import models.Parking;
import models.User;

@Dao
public interface UserDao {
    @Query("select * from User where id LIKE :userId")
    LiveData<User> getUser(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
