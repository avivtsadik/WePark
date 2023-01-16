package services;

public abstract class AbstractLoginService {
    public abstract void signOut();
    public abstract String getUserId();
    public abstract String getUserName();
    public abstract String getUserEmail();
}
