package com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.domain;


public class Review {

    private int id;
    private final int novelId;
    private final String reviewer;
    private final String comment;
    private final int rating;

    public Review(int novelId, String reviewer, String comment, int rating) {
        this.novelId = novelId;
        this.reviewer = reviewer;
        this.comment = comment;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNovelId() {
        return novelId;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}

