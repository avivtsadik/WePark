package services;

public class LoginService {
    public static final LoginService _instance = new LoginService();

    public static LoginService instance() {
        return _instance;
    }

    private AbstractLoginService loginService;

    private LoginService() {
    }

    public void setLoginService(AbstractLoginService loginService) {
        this.loginService = loginService;
    }

    public AbstractLoginService getLoginService() {
        return loginService;
    }
}
