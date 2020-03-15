package org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig;


public class TankerKoenigData {

    private String id;
    private String name;
    private String brand;
    private String street;
    private String placeNamer;
    private double geographicLatitude;
    private double geographicLongitude;
    private double distance;
    private double price;
    private boolean isOpen;
    private String houseNumber;
    private int postCode;

    public TankerKoenigData(String id, String name, String brand, String street, String placeNamer, double geographicLatitude, double geographicLongitude, double distance, double price, boolean isOpen, String houseNumber, int postCode) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.street = street;
        this.placeNamer = placeNamer;
        this.geographicLatitude = geographicLatitude;
        this.geographicLongitude = geographicLongitude;
        this.distance = distance;
        this.price = price;
        this.isOpen = isOpen;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
    }

    public TankerKoenigData() {
        this.id = "emptyID";
        this.name = "name";
        this.brand = "brand";
        this.street = "street";
        this.placeNamer = "placeName";
        this.geographicLatitude = 0;
        this.geographicLongitude = 0;
        this.distance = 0;
        this.price = 0;
        this.isOpen = true;
        this.houseNumber = "houseNumber";
        this.postCode = 12345;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getStreet() {
        return street;
    }

    public String getPlaceNamer() {
        return placeNamer;
    }

    public double getGeographicLatitude() {
        return geographicLatitude;
    }

    public double getGeographicLongitude() {
        return geographicLongitude;
    }

    public double getDistance() {
        return distance;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }


}
