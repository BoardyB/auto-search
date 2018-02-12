package com.github.boardyb.jofogas.search;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SearchListElement {

    private String id;
    private String subject;
    private int pictureCount;
    private int price;
    //    TODO: Map categories to wrapper enum
    private String category;
    private LocalDateTime uploadDate;
    //    TODO: Map badges to wrapper enum
    private ArrayList<String> badge;
    private boolean validPhoneNumber;

    public SearchListElement(String id,
                             String subject,
                             int pictureCount,
                             int price,
                             String category,
                             LocalDateTime uploadDate,
                             ArrayList<String> badge,
                             boolean validPhoneNumber) {
        this.id = id;
        this.subject = subject;
        this.pictureCount = pictureCount;
        this.price = price;
        this.category = category;
        this.uploadDate = uploadDate;
        this.badge = badge;
        this.validPhoneNumber = validPhoneNumber;
    }

    public SearchListElement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(int pictureCount) {
        this.pictureCount = pictureCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ArrayList<String> getBadge() {
        return badge;
    }

    public void setBadge(ArrayList<String> badge) {
        this.badge = badge;
    }

    public boolean isValidPhoneNumber() {
        return validPhoneNumber;
    }

    public void setValidPhoneNumber(boolean validPhoneNumber) {
        this.validPhoneNumber = validPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchListElement that = (SearchListElement) o;

        return subject != null ? subject.equals(that.subject) : that.subject == null;
    }

    @Override
    public int hashCode() {
        return subject != null ? subject.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchListElement{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", pictureCount=").append(pictureCount);
        sb.append(", price=").append(price);
        sb.append(", category='").append(category).append('\'');
        sb.append(", uploadDate=").append(uploadDate);
        sb.append(", badge='").append(badge).append('\'');
        sb.append(", validPhoneNumber=").append(validPhoneNumber);
        sb.append('}');
        return sb.toString();
    }
}
    
