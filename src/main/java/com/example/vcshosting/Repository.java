package com.example.vcshosting;

public class Repository {
    private String name;
    private String url;
    private boolean highlight;

    public Repository() {}

    public Repository(String name, String url, boolean highlight) {
        this.name = name;
        this.url = url;
        this.highlight = highlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
}