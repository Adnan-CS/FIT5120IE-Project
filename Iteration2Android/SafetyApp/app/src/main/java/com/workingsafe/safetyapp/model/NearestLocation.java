package com.workingsafe.safetyapp.model;

public class NearestLocation {
    private PoliceStation policeStation;
    private SevenEleven sevenEleven;
    private Hospital hospital;
    public NearestLocation(){}

    public NearestLocation(PoliceStation policeStation, SevenEleven sevenEleven, Hospital hospital) {
        this.policeStation = policeStation;
        this.sevenEleven = sevenEleven;
        this.hospital = hospital;
    }

    public PoliceStation getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(PoliceStation policeStation) {
        this.policeStation = policeStation;
    }

    public SevenEleven getSevenEleven() {
        return sevenEleven;
    }

    public void setSevenEleven(SevenEleven sevenEleven) {
        this.sevenEleven = sevenEleven;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
