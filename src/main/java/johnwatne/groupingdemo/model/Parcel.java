package johnwatne.groupingdemo.model;

public class Parcel {
    private UniqueTaxingArea parentUta;
    private String parcelId;

    public Parcel(final UniqueTaxingArea parentUta, final String parcelId) {
        this.parentUta = parentUta;
        this.parcelId = parcelId;
    }

    public UniqueTaxingArea getParentUta() {
        return parentUta;
    }

    public void setParentUta(final UniqueTaxingArea parentUta) {
        this.parentUta = parentUta;
    }

    public String getParcelId() {
        return parcelId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parentUta == null) ? 0 : parentUta.hashCode());
        result = prime * result + ((parcelId == null) ? 0 : parcelId.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Parcel other = (Parcel) obj;
        if (parentUta == null) {
            if (other.parentUta != null)
                return false;
        } else if (!parentUta.equals(other.parentUta))
            return false;
        if (parcelId == null) {
            if (other.parcelId != null)
                return false;
        } else if (!parcelId.equals(other.parcelId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Parcel [parentUta=" + parentUta + ", parcelId=" + parcelId + "]";
    }

    public void setParcelId(final String parcelId) {
        this.parcelId = parcelId;
    }
}
