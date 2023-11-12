package johnwatne.groupingdemo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Submission {
    private BigDecimal submissionInternalId;
    private List<UniqueTaxingArea> utas = new ArrayList<>();

    public Submission(final BigDecimal submissionInternalId) {
        this.submissionInternalId = submissionInternalId;
    }

    public BigDecimal getSubmissionInternalId() {
        return submissionInternalId;
    }

    public void setSubmissionInternalId(final BigDecimal submissionInternalId) {
        this.submissionInternalId = submissionInternalId;
    }

    public List<UniqueTaxingArea> getUtas() {
        return utas;
    }

    public void setUtas(final List<UniqueTaxingArea> utas) {
        this.utas = utas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((submissionInternalId == null) ? 0 : submissionInternalId.hashCode());
        result = prime * result + ((utas == null) ? 0 : utas.hashCode());
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
        final Submission other = (Submission) obj;
        if (submissionInternalId == null) {
            if (other.submissionInternalId != null)
                return false;
        } else if (!submissionInternalId.equals(other.submissionInternalId))
            return false;
        if (utas == null) {
            if (other.utas != null)
                return false;
        } else if (!utas.equals(other.utas))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Submission [submissionInternalId=" + submissionInternalId + "]";
    }
}
