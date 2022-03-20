package cm.app.db;

import java.util.Date;

public class Rent {
    private int id;
    private int stroller;
    private int user;
    private Date begin;
    private Date end;
    private Date prev;

    public Rent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStroller() {
        return stroller;
    }

    public void setStroller(int stroller) {
        this.stroller = stroller;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getPrev() {
        return prev;
    }

    public void setPrev(Date prev) {
        this.prev = prev;
    }
}
