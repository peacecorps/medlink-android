package gov.peacecorps.medlinkandroid.activities.createrequest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.data.models.Supply;
import gov.peacecorps.medlinkandroid.helpers.DataManager;

public class SupplyListAdapter extends RecyclerView.Adapter<SupplyListAdapter.SupplyViewHolder> {

    private final CreateRequestView createRequestView;
    private List<Supply> supplies;
    private final Set<Integer> selectedSupplyIds, checkedListPositions;

    public SupplyListAdapter(CreateRequestView createRequestView, DataManager dataManager) {
        this.createRequestView = createRequestView;
        this.selectedSupplyIds = new HashSet<>();
        this.checkedListPositions = new HashSet<>();

        initSuppliesList(dataManager);
    }

    private void initSuppliesList(DataManager dataManager) {
        this.supplies = dataManager.getSupplies();
        notifyDataSetChanged();
    }

    @Override
    public SupplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_requests_create_request_supply, parent, false);
        return new SupplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SupplyViewHolder holder, final int position) {
        final Supply supply = supplies.get(position);
        holder.supplyNameTv.setText(supply.getNameAndShortCode());

        holder.supplySelectCb.setOnCheckedChangeListener(null);
        holder.supplySelectCb.setChecked(checkedListPositions.contains(position));
        holder.supplySelectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedSupplyIds.add(supply.getId());
                    checkedListPositions.add(position);
                } else {
                    selectedSupplyIds.remove(supply.getId());
                    checkedListPositions.remove(position);
                }

                createRequestView.enableSubmitBtn(!checkedListPositions.isEmpty());
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplies.size();
    }

    public Set<Integer> getSelectedSupplyIds() {
        return this.selectedSupplyIds;
    }

    public static class SupplyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.supplyNameTv)
        TextView supplyNameTv;

        @Bind(R.id.supplySelectCb)
        CheckBox supplySelectCb;

        public SupplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
