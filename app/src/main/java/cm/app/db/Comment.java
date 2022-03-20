package cm.app.db;

import java.util.Date;

public class Comment {
    private int id;
    private int user;
    private Date date;
    private String content;
    private int rating;

    public Comment() {
    }

    public Comment(int id, int user, Date date, String content, int rating) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.content = content;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
