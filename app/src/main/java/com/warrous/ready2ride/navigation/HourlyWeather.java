package com.warrous.ready2ride.navigation;

public class HourlyWeather {

    private String weather_icon;

    private double main_temp, clouds_all, wind_speed;

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    public double getMain_temp() {
        return main_temp;
    }

    public void setMain_temp(double main_temp) {
        this.main_temp = main_temp;
    }

    public double getClouds_all() {
        return clouds_all;
    }

    public void setClouds_all(double clouds_all) {
        this.clouds_all = clouds_all;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    @Override
    public String toString() {
        return "HourlyWeather [weather_icon=" + weather_icon + ", main_temp="
                + main_temp + ", clouds_all=" + clouds_all + ", wind_speed="
                + wind_speed + "]";
    }
}
