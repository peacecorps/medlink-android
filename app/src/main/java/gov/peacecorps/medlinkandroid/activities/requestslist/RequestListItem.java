package gov.peacecorps.medlinkandroid.activities.requestslist;

import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;

public final class RequestListItem extends Request {
    private boolean isSectionHeader;
    private String sectionHeaderName;

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
}
