package models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserFragmentViewModel extends ViewModel {
    private LiveData<User> user = UserMock.instance().getUser();

    public LiveData<User> getUser() {
        return user;
    }
}
