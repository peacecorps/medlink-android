package gov.peacecorps.medlinkandroid.ui.activities.createrequest;

import android.support.annotation.NonNull;
import android.telephony.SmsManager;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Set;

import gov.peacecorps.medlinkandroid.R;
import gov.peacecorps.medlinkandroid.ui.activities.BaseActivity;
import gov.peacecorps.medlinkandroid.helpers.DataManager;
import gov.peacecorps.medlinkandroid.helpers.UiUtils;
import gov.peacecorps.medlinkandroid.rest.GlobalRestCallback;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.response.createrequest.SubmitNewRequestResponse;
import gov.peacecorps.medlinkandroid.rest.service.API;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class CreateRequestPresenter {
    private final API api;
    private final DataManager dataManager;
    private BaseActivity baseActivity;

    public CreateRequestPresenter(CreateRequestView createRequestView, API api, DataManager dataManager) {
        this.baseActivity = createRequestView.getBaseActivity();
        this.api = api;
        this.dataManager = dataManager;
    }

    public void submitNewRequest(Set<Integer> selectedSupplyIds, String specialInstructions) {
        baseActivity.showProgressDialog(R.string.submitting_new_order);
        final SubmitNewRequest newRequestPayload = buildNewRequestPayload(selectedSupplyIds, specialInstructions);
        final MaterialDialog.ButtonCallback finishActivityCallback = new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                baseActivity.finish();
            }
        };

        final GlobalRestCallback.NetworkFailureCallback networkFailureCallback = new GlobalRestCallback.NetworkFailureCallback() {
            @Override
            public void onNetworkFailure() {
                createOrderByText();
            }

            private void createOrderByText() {
                UiUtils.showAlertDialog(baseActivity,
                        R.string.send_order_by_sms,
                        R.string.yes,
                        R.string.no,
                        new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(baseActivity.getString(R.string.sms_phone_number), null, buildTextBody(), null, null);
                                baseActivity.showInfoDialog(R.string.your_order_has_been_submitted, finishActivityCallback);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                dataManager.setUnsubmittedRequest(newRequestPayload);
                                baseActivity.showInfoDialog(R.string.your_order_has_been_saved, finishActivityCallback);
                            }
                        });
            }

            private String buildTextBody() {
                String supplyShortCodes = getSupplyShortCodes();
                String specialInstructions = newRequestPayload.getSpecialInstructions();
                if (!specialInstructions.isEmpty()) {
                    return supplyShortCodes + " - " + specialInstructions;
                }

                return supplyShortCodes;
            }

            @NonNull
            private String getSupplyShortCodes() {
                StringBuilder sb = new StringBuilder();
                for (Integer supplyId : newRequestPayload.getSupplyIds()) {
                    sb.append(dataManager.getSupply(supplyId).getShortCode());
                    sb.append(", ");
                }

                String result = sb.toString();
                if (result.isEmpty())
                    return result;
                else
                    return trimTrailingComma(result);
            }

            private String trimTrailingComma(String result) {
                return result.substring(0, result.length() - 2);
            }
        };

        Call<SubmitNewRequestResponse> submitNewRequestCall = api.submitNewRequest(newRequestPayload);
        submitNewRequestCall.enqueue(new GlobalRestCallback<SubmitNewRequestResponse>(baseActivity, networkFailureCallback) {
            @Override
            public void onResponse(Response<SubmitNewRequestResponse> response, Retrofit retrofit) {
                baseActivity.dismissProgressDialog();
                if (response.isSuccess()) {
                    baseActivity.showInfoDialog(R.string.your_order_has_been_submitted, finishActivityCallback);
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
