package room;

import androidx.room.Room;

import application.WeParkApplication;

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