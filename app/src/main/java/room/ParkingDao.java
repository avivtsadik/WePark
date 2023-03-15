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

    @Query("select * from Parking where id LIKE :parkingId")
    LiveData<Parking> getParking(String parkingId);

    @Query("DELETE FROM Parking")
    void deleteAll();

    @Delete
    void delete(Parking parking);
}
