package gov.peacecorps.medlinkandroid.activities.requestdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.UiUtils;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class SupplyListAdapter extends RecyclerView.Adapter<SupplyListAdapter.SupplyViewHolder> {

    private final RequestDetailView requestDetailView;
    private final List<Supply> suppliesList;
    private final AppSharedPreferences appSharedPreferences;
    private final API api;

    public SupplyListAdapter(RequestDetailView requestDetailView, AppSharedPreferences appSharedPreferences, API api) {
        this.requestDetailView = requestDetailView;
        this.appSharedPreferences = appSharedPreferences;
        this.api = api;
        this.suppliesList = new LinkedList<>();
    }

    @Override
    public SupplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_detail_supply, parent, false);

        return new SupplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SupplyViewHolder holder, int position) {
        final Supply supply = suppliesList.get(position);
        gov.peacecorps.medlinkandroid.data.models.Supply supplyModel = appSharedPreferences.getSupply(supply.getId());
        holder.supplyNameTv.setText(supplyModel.getName());
        holder.supplyActionBtn.setVisibility(View.GONE);

        final Context context = requestDetailView.getBaseActivity();
        String supplyStatus;
        if (supply.isPending()) {
            supplyStatus = context.getString(R.string.not_processed_by_pcmo);
        } else {
            switch (supply.getResponse().getType()) {
                case DELIVERY:
                    supplyStatus = context.getString(R.string.approved_for_delivery);
                    holder.supplyActionBtn.setVisibility(View.VISIBLE);
                    holder.supplyActionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UiUtils.showAlertDialog(context,
                                    R.string.are_you_sure_received,
                                    R.string.yes,
                                    R.string.no,
                                    new MaterialDialog.ButtonCallback() {
                                        @Override
                                        public void onPositive(MaterialDialog dialog) {
                                            super.onPositive(dialog);
                                            markSupplyAsReceived(supply);
                                        }
                                    });
                        }

                        private void markSupplyAsReceived(Supply supply) {
                            Call<Void> baseResponseCall = api.markSupplyAsReceived(supply.getResponse().getId());
                            final BaseActivity baseActivity = requestDetailView.getBaseActivity();
                            baseActivity.showProgressDialog(R.string.marking_supply_as_received);
                            baseResponseCall.enqueue(new GlobalRestCallback<Void>(baseActivity) {
                                @Override
                                public void onResponse(Response<Void> response, Retrofit retrofit) {
                                    baseActivity.dismissProgressDialog();
                                    if(response.isSuccess()) {
                                        holder.supplyActionBtn.setEnabled(false);
                                    } else {
                                        baseActivity.showInfoDialog(R.string.we_are_having_technical_issues);
                                    }
                                }
                            });
                        }
                    });
                    break;
                case DENIAL:
                    supplyStatus = context.getString(R.string.denied_by_pcmo);
                    break;
                case PICKUP:
                    supplyStatus = context.getString(R.string.scheduled_for_pickup);
                    break;
                default:
                    supplyStatus = context.getString(R.string.will_be_reimbursed);
            }
        }

        holder.supplyStatusTv.setText(supplyStatus);
    }

    @Override
    public int getItemCount() {
        return suppliesList.size();
    }

    public void updateSupplies(List<Supply> supplies) {
        suppliesList.clear();
        suppliesList.addAll(supplies);
        notifyDataSetChanged();
    }

    public static class SupplyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.supplyActionBtn)
        ImageButton supplyActionBtn;

        @Bind(R.id.supplyNameTv)
        TextView supplyNameTv;

        @Bind(R.id.supplyStatusTv)
        TextView supplyStatusTv;

        public SupplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
