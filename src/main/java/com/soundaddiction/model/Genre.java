package com.soundaddiction.model;

import com.soundaddiction.util.Checker;

public class Genre {

    private int genreId;
    private String value;

    public Genre(int genreId, String value){
        this.setGenreId(genreId);
        this.setValue(value);
    }

    public void setValue(String value) {
        if(Checker.isNotNullOrEmpty(value)) {
            this.value = value;
        }
    }

    public void setGenreId(int genreId){
        if(genreId > 0){
            this.genreId = genreId;
        }
    }

    public int getGenreId(){
        return this.genreId;
    }

    public String getValue(){
        return this.value;
    }

}
