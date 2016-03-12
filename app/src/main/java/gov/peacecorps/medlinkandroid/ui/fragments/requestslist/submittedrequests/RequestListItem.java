package gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests;

import android.content.Context;

import java.io.Serializable;
import java.util.Date;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.helpers.Constants;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;

public final class RequestListItem extends Request implements Serializable {
    private boolean isSubSectionHeader;
    private String subSectionHeaderName;

    public boolean isSubSectionHeader() {
        return isSubSectionHeader;
    }

    public void setIsSubSectionHeader(boolean isSectionHeader) {
        this.isSubSectionHeader = isSectionHeader;
    }

    public String getSubSectionHeaderName() {
        return subSectionHeaderName;
    }

    public void setSubSectionHeaderName(String subSectionHeaderName) {
        this.subSectionHeaderName = subSectionHeaderName;
    }

    public String getDateString(Context context) {
        Date createdAt = getCreatedAt();
        if (createdAt == null) {
            return context.getString(R.string.not_submitted);
        }

        return Constants.DISPLAY_SIMPLE_DATE_FORMAT.format(createdAt);
    }
}