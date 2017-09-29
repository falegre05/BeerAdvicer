package com.example.ferzi.myapplication;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by ferzi on 08/09/2017.
 */

public class Beer implements Comparable<Beer>, Serializable {

    private String name;
    private String abv;
    private String ibu;
    private String img;
    private int style_rating;
    private double func_pertenencia;
    private String brewery;

    public Beer(String name, String abv, String ibu, String img, int style_rating, double func_pertenencia, String brewery) {
        this.name = name;
        this.abv = abv;
        this.ibu = ibu;
        this.img = img;
        this.style_rating = style_rating;
        this.func_pertenencia = func_pertenencia;
        this.brewery = brewery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStyle_rating() {
        return style_rating;
    }

    public void setStyle_rating(int style_rating) {
        this.style_rating = style_rating;
    }

    public double getFunc_pertenencia() {
        return func_pertenencia;
    }

    public void setFunc_pertenencia(double func_pertenencia) {
        this.func_pertenencia = func_pertenencia;
    }

    @Override
    public int compareTo(Beer b) {
        if (func_pertenencia > b.func_pertenencia){
            return -1;
        } else if (func_pertenencia < b.func_pertenencia) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", abv='" + abv + '\'' +
                ", ibu='" + ibu + '\'' +
                ", img='" + img + '\'' +
                ", style_rating=" + style_rating +
                ", func_pertenencia=" + func_pertenencia +
                ", brewery='" + brewery + '\'' +
                '}';
    }
}