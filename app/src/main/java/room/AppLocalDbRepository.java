package room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import models.Parking;
import models.User;
import models.UserConverter;

@Database(entities = {Parking.class,User.class}, version = 12)
@TypeConverters({UserConverter.class})
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ParkingDao parkingDao();
    public abstract UserDao userDao();
}
