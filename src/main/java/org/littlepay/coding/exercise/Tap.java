package org.littlepay.coding.exercise;

import  com.univocity.parsers.annotations.*;

import java.util.Date;

public class Tap {
    @Parsed(field = "ID")
    private Long id;

    @Parsed(field = "DateTimeUTC")
    @Format(formats = {"dd-MM-YYYY HH:mm:ss"}, options = "locale=en;lenient=false")
    private Date dateTimeUTC;

    @Parsed(field = "TapType")
    private String tapType;

    @Parsed(field = "StopId")
    private String stopId;

    @Parsed(field = "CompanyId")
    private String companyId;

    @Parsed(field = "BusID")
    private String busID;

    @Parsed(field = "PAN")
    private String pan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTimeUTC() {
        return dateTimeUTC;
    }

    public void setDateTimeUTC(Date dateTimeUTC) {
        this.dateTimeUTC = dateTimeUTC;
    }

    public String getTapType() {
        return tapType;
    }

    public void setTapType(String tapType) {
        this.tapType = tapType;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}