package com.passershowe.gank.data.entity;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PassersHowe on 2016/8/27.
 */

public class GankData implements Serializable {
    boolean error;
     public ArrayList<Gank> results;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<Gank> getResults() {
        return results;
    }

    public void setResults(ArrayList<Gank> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankData{" +
                "error='" + error + '\'' +
                ", results=" + results +
                '}';
    }
}
