package room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import java.util.Vector;

import models.Parking;

@Dao
public interface ParkingDao {
    @Query("select * from Parking")
    LiveData<List<Parking>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Parking... parkings);

    @Delete
    void delete(Parking parking);
}
