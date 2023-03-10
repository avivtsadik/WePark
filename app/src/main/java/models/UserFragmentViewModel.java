package models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import services.LoginService;

public class UserFragmentViewModel extends ViewModel {
    private LiveData<User> user = UserMock.instance().getUser(LoginService.instance().getLoginService().getUserId());

    public LiveData<User> getUser() {
        return user;
    }
}
