package org.littlepay.coding.exercise;

import com.univocity.parsers.annotations.Parsed;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//import java.util.Date;

public class Trip {

    @Parsed(field = "Started")
    private Date started;

    @Parsed(field = "Finished")
    private Date finished;

    @Parsed(field = "DurationSecs")
    private long durationSecs;

    @Parsed(field = "FromStopId")
    private String fromStopId;

    @Parsed(field = "ToStopId")
    private String toStopId;

    @Parsed(field = "ChargeAmount")
    private String chargeAmount;

    @Parsed(field = "CompanyId")
    private String companyId;

    @Parsed(field = "BusID")
    private String busID;

    @Parsed(field = "PAN")
    private String pan;

    @Parsed(field = "Status")
    private String status;

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public long getDurationSecs() {
        durationSecs = (started != null && finished != null ) ?
                ChronoUnit.SECONDS.between(LocalDateTime.ofInstant(started.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(finished.toInstant(), ZoneId.systemDefault())) : 0;
        return durationSecs;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }

    public String getChargeAmount() {
        switch (fromStopId.toLowerCase()){
            case "stop1": chargeAmount = (toStopId != null && toStopId.equalsIgnoreCase("stop2"))  ? "3.25" : "7.30";
            break;
            case "stop2": chargeAmount = (toStopId != null && toStopId.equalsIgnoreCase("stop1"))  ? "3.25" : "5.50";
            break;
            case "stop3": chargeAmount = (toStopId != null && toStopId.equalsIgnoreCase("stop2"))  ? "5.50" : "7.30";
        }
        chargeAmount = (toStopId != null && toStopId.equalsIgnoreCase(fromStopId))  ? "0" : chargeAmount;
        return chargeAmount;
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

    public String getStatus() {
        status = ((fromStopId.equalsIgnoreCase(toStopId))  ? "cancelled" : (toStopId.equalsIgnoreCase("unknown")) ? "incomplete" : "complete");
        return status;
    }
}
