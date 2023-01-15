package room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import models.Parking;

@Database(entities = {Parking.class}, version = 55)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ParkingDao parkingDao();
}
