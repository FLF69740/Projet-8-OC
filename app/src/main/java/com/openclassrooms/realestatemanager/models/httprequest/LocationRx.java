package com.openclassrooms.realestatemanager.models.httprequest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationRx {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    public class Result {
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;
        @SerializedName("formatted_address")
        @Expose
        private String formattedAddress;

        public Geometry getGeometry() {
            return geometry;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }
    }

    public class Geometry {
        @SerializedName("location")
        @Expose
        private Location_ location;

        public Location_ getLocation() {
            return location;
        }
    }

    public class Location_ {
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public Double getLng() {
            return lng;
        }
    }
}
