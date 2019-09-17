package com.example.bookzone;

public class ImageUploadInfo {

    String title;
    String image;
    String des;
    String author;
    String price;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    String search;

    public ImageUploadInfo() {
    }

    public ImageUploadInfo(String title, String image, String des, String author, String price,String search,String status) {
        this.title = title;
        this.image = image;
        this.des = des;
        this.author = author;
        this.price = price;
        this.search=search;
        this.status=status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
