package models;

public class Favorite {
    private String city;
    private String neighborhood;

    public Favorite(String city, String neighborhood) {
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
