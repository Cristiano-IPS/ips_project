package cm.app.db;

import androidx.annotation.NonNull;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String contact;
    private String password;
    private Date birthdate;
    private int parentage;
    private int country;
    private boolean delete;
    private float lat;
    private float lon;

    public User() { }

    @NonNull
    @Override
    public String toString() {
        return name+" "+contact+" "+password+" "+id;
    }

    public User(String name, String contact, String password, Date birthdate, int country, int parentage) {
        super();
        this.name = name;
        this.contact = contact;
        this.password = password;
        this.birthdate = birthdate;
        this.parentage = parentage;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getParentage() {
        return parentage;
    }

    public void setParentage(int parentage) {
        this.parentage = parentage;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
