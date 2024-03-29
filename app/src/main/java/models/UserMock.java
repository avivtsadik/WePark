package models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import models.Interfaces.OnActionDoneListener;
import room.AppLocalDb;
import room.AppLocalDbRepository;
import services.LoginService;

public class UserMock {
    public static final UserMock _instance = new UserMock();
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb;
    final public MutableLiveData<LoadingState> EventUserListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING_USER);
    private LiveData<User> user;

    public static UserMock instance() {
        return _instance;
    }

    public UserMock() {

    }

    public LiveData<User> getUser() {
        String userId = LoginService.instance().getLoginService().getUserId();

        if (user == null) {
            user = localDb.userDao().getUser(userId);
            refreshUser(userId);
        }

        return user;
    }

    public void refreshUser(String userId) {
        EventUserListLoadingState.setValue(LoadingState.LOADING_USER);
        // get local last update
        Long localLastUpdate = User.getLocalLastUpdate();
        // get all updated records from firebase since local update
        firebaseModel.getUser(userId, newUser -> {
            executor.execute(() -> {
                Log.d("TAG", " firebase return : " + newUser);
                Long time = localLastUpdate;
                //insert new records into ROOM
                User user = (User) newUser;
                localDb.userDao().insertAll(user);
                if (user.getLastUpdated() != null && time < user.getLastUpdated()) {
                    time = user.getLastUpdated();
                }
                //update local last update
                User.setLocalLastUpdate(time);
                EventUserListLoadingState.postValue(LoadingState.NOT_LOADING_USER);
            });
        });
    }

    public void updateUserData(User user, OnActionDoneListener<Void> listener) {
        firebaseModel.updateUserData(user, (Void) -> {
            refreshUser(user.getId());
            listener.onComplete(null);
        });
    }

    public void getExistingUser(String userId, OnActionDoneListener<User> listener) {
        firebaseModel.getUser(userId, listener);
    }

    public void uploadProfileImage(Bitmap bitmap, String userId, OnActionDoneListener<String> listener) {
        firebaseModel.uploadImage(bitmap, userId, listener);
    }

}
