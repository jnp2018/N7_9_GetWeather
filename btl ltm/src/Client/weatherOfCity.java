/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.Date;

/**
 *
 * @author tun
 */
public class weatherOfCity {

    private String cityName,lastUpdate;
    private String  may, thoitiet;
    private float nhietDo, doAm, apSuat, tamNhin;
    public weatherOfCity() {
    }

    public weatherOfCity(String cityName, String may, float nhietDo, float doAm, float apSuat, float tamNhin, String lastUpdate, String thoitiet) {
        this.cityName = cityName;
        this.may = may;
        this.nhietDo = nhietDo;
        this.doAm = doAm;
        this.apSuat = apSuat;
        this.tamNhin = tamNhin;
        this.lastUpdate = lastUpdate;
        this.thoitiet = thoitiet;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public float getNhietDo() {
        return nhietDo;
    }

    public void setNhietDo(float nhietDo) {
        this.nhietDo = nhietDo;
    }

    public float getDoAm() {
        return doAm;
    }

    public void setDoAm(float doAm) {
        this.doAm = doAm;
    }

    public float getApSuat() {
        return apSuat;
    }

    public void setApSuat(float apSuat) {
        this.apSuat = apSuat;
    }

    public float getTamNhin() {
        return tamNhin;
    }

    public void setTamNhin(float tamNhin) {
        this.tamNhin = tamNhin;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getThoitiet() {
        return thoitiet;
    }

    public void setThoitiet(String thoitiet) {
        this.thoitiet = thoitiet;
    }
    
    public Object[] toObject() {

        return new Object[]{this.cityName,this.thoitiet,String.format("%.2f", this.nhietDo), this.doAm, this.apSuat, this.may, this.tamNhin};
    }
}
