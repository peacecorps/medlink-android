package gov.peacecorps.medlinkandroid.activities.requestslist;

import java.io.Serializable;

import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;

public final class RequestListItem extends Request implements Serializable {
    private boolean isSectionHeader;
    private String sectionHeaderName;
    private boolean isSubSectionHeader;
    private String subSectionHeaderName;

    public boolean isSectionHeader() {
        return isSectionHeader;
    }

    public void setIsSectionHeader(boolean isSectionHeader) {
        this.isSectionHeader = isSectionHeader;
    }

    public String getSectionHeaderName() {
        return sectionHeaderName;
    }

    public void setSectionHeaderName(String sectionHeaderName) {
        this.sectionHeaderName = sectionHeaderName;
    }

    public boolean isSubSectionHeader() {
        return isSubSectionHeader;
    }

    public void setIsSubSectionHeader(boolean isSubSectionHeader) {
        this.isSubSectionHeader = isSubSectionHeader;
    }

    public String getSubSectionHeaderName() {
        return subSectionHeaderName;
    }

    public void setSubSectionHeaderName(String subSectionHeaderName) {
        this.subSectionHeaderName = subSectionHeaderName;
    }
}
