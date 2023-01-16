package room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import models.Parking;

@Database(entities = {Parking.class}, version = 2)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ParkingDao parkingDao();
}