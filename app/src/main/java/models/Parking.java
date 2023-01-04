package models;

public class Parking {
    private int id;
    private String city;
    private String street;
    private ParkingSize size;

    public Parking(int id, String city, String street, ParkingSize size) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public ParkingSize getSize() {
        return size;
    }

    public void setSize(ParkingSize size) {
        this.size = size;
    }
}
