package gov.peacecorps.medlinkandroid.activities.createrequest;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Set;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.BaseResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class CreateRequestPresenter {
    private final API api;
    private BaseActivity baseActivity;

    public CreateRequestPresenter(CreateRequestView createRequestView, API api) {
        this.baseActivity = createRequestView.getBaseActivity();
        this.api = api;
    }

    public void submitNewRequest(Set<Integer> selectedSupplyIds, String specialInstructions) {
        Call<BaseResponse> submitNewRequestCall = api.submitNewRequest(buildNewRequestPayload(selectedSupplyIds, specialInstructions));
        baseActivity.showProgressDialog(R.string.submitting_new_order);
        submitNewRequestCall.enqueue(new GlobalRestCallback<BaseResponse>(baseActivity) {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                baseActivity.dismissProgressDialog();
                if (response.isSuccess()) {
                    baseActivity.showInfoDialog(R.string.your_order_has_been_submitted,
                            new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    baseActivity.finish();
                                }
                            });
                } else {
                    baseActivity.showInfoDialog(R.string.we_are_having_technical_issues);
                }
            }
        });
    }

    private SubmitNewRequest buildNewRequestPayload(Set<Integer> selectedSupplyIds, String specialInstructions) {
        SubmitNewRequest payload = new SubmitNewRequest();
        payload.setSupplyIds(selectedSupplyIds);
        payload.setSpecialInstructions(specialInstructions);

        return payload;
    }
}
