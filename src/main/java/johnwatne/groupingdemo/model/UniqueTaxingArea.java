package johnwatne.groupingdemo.model;

import java.util.ArrayList;
import java.util.List;

public class UniqueTaxingArea {
    private Submission parentSubmission;
    private List<Parcel> parcels = new ArrayList<>();
    private String countyCode;
    private String city;

    public UniqueTaxingArea(final Submission parentSubmission, final String county, final String city) {
        this.parentSubmission = parentSubmission;
        this.countyCode = county;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Submission getParentSubmission() {
        return parentSubmission;
    }

    public void setParentSubmission(final Submission parentSubmission) {
        this.parentSubmission = parentSubmission;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(final List<Parcel> parcels) {
        this.parcels = parcels;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(final String countyCode) {
        this.countyCode = countyCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parentSubmission == null) ? 0 : parentSubmission.hashCode());
        result = prime * result + ((countyCode == null) ? 0 : countyCode.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniqueTaxingArea other = (UniqueTaxingArea) obj;
        if (parentSubmission == null) {
            if (other.parentSubmission != null)
                return false;
        } else if (!parentSubmission.equals(other.parentSubmission))
            return false;
        if (countyCode == null) {
            if (other.countyCode != null)
                return false;
        } else if (!countyCode.equals(other.countyCode))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UniqueTaxingArea [parentSubmission=" + parentSubmission + ", countyCode=" + countyCode + ", city="
                + city + "]";
    }

}
