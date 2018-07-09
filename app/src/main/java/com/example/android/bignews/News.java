package com.example.android.bignews;

public class News  {

    private String header;
    private String author;
    private String category;
    private String date;
    private String url;
    public News( String header, String author, String category, String date, String url) {

      this.author = author;
      this.header = header;
      this.category = category;
      this.date = date;
      this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
