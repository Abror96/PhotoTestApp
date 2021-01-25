package com.abbasov.testphotoapp.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("name")
    @Expose
    private String name;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class Main {

        @SerializedName("temp")
        @Expose
        private Double temp;
        @SerializedName("humidity")
        @Expose
        private Long humidity;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Long getHumidity() {
            return humidity;
        }

        public void setHumidity(Long humidity) {
            this.humidity = humidity;
        }

    }

    public class Clouds {

        @SerializedName("all")
        @Expose
        private Long all;

        public Long getAll() {
            return all;
        }

        public void setAll(Long all) {
            this.all = all;
        }

    }

}
