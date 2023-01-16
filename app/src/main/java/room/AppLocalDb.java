package room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import application.WeParkApplication;
import models.Parking;

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb =
            Room.databaseBuilder(WeParkApplication.getAppContext(),
                            AppLocalDbRepository.class,
                            "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();


    private AppLocalDb() {
    }
}