package gov.peacecorps.medlinkandroid.ui.fragments.requestslist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;

public final class RequestViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.rowLayout)
    public
    RelativeLayout rowLayout;

    @Bind(R.id.orderDateTv)
    public
    TextView orderDateTv;

    @Bind(R.id.suppliesSnippetTv)
    public
    TextView suppliesSnippetTv;

    public RequestViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}