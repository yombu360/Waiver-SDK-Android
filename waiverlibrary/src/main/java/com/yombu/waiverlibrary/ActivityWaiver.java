package com.yombu.waiverlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWaiver extends ActivityBase {

    private LinearLayout parentLayout;
    private RelativeLayout rlWaiver, rlProcessing;
    private RelativeLayout rlWaiverContent, rlInternetProblem, rlLoading, signatureWindow;
    private ImageView animatedProcessingView;
    private TextView tvTitle, tvInstructions, tvClear, tvName, tvDate, tvElectronicSignatureConsent, tvAgree, tvTermsOfUse;
    private Button btnAccept, btnRetry, btnCancel;
    private CheckBox cbElectronicSignatureConsent, cbReceiveEmailNotification, cbYombuTermsOfUse;
    private TextInputLayout etElectronicSignatureConsentLayout, etSignatureLayout, etYombuTermsOfUseLayout;
    private ScrollView scrollView;
    private MaterialButton scrollButton;
    private LinearLayout llWaiverContent, llWaiverCheckboxOptions;

    private List<CheckBox> cbOptionList;
    private List<TextInputLayout> etOptionLayoutList;
    private List<ModelCheckboxOption> checkboxOptions;

    private List<RelativeLayout> rlInitialBoxes = new ArrayList<>();
    private List<TextInputLayout> etInitialLayouts = new ArrayList<>();
    private List<TextView> tvInitialClears = new ArrayList<>();

    private List<ViewSignatureDrawable> initialDrawables = new ArrayList<>();
    private List<Handler> initialTimers = new ArrayList<>();
    private List<Runnable> initialValidities = new ArrayList<>();

    private int numberOfInitials;

    private ViewSignatureDrawable signatureDrawable;
    private boolean waiverLoaded;

    private Handler signatureTimer;
    private boolean hidingScrollButton;

    private Runnable signatureValidity = new Runnable() {
        @Override
        public void run() {
            if (signatureDrawable.hasTouches()) {
                updateAcceptButton();
                etSignatureLayout.setError(null);
            } else {
                signatureTimer.postDelayed(this, 200);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiver);

        cbOptionList = new ArrayList<>();
        etOptionLayoutList = new ArrayList<>();
        checkboxOptions = terminal.getDeviceConfig(this).getWaiverCheckboxOptions();

        initializeViews();
        attachListeners();

        updateViews();

        waiverLoaded = false;
        hidingScrollButton = false;

        enableSignature();
        updateAcceptButton();
    }

    private void updateViews() {
        Glide.with(getApplicationContext()).load(R.drawable.busy_indicator).into(animatedProcessingView);

        String name;
        if (session.getGuardianFirstName() != null || session.getGuardianLastName() != null) {
            name = session.getGuardianFirstName() + " " + session.getGuardianLastName();
        } else {
            name = session.getFirstName() + " " + session.getLastName();
        }
        DateFormat df = new SimpleDateFormat(Constants.WAIVER_SCREEN_DATE_FORMAT, Locale.US);
        String date = df.format(Calendar.getInstance().getTime());

        tvName.setText(name);
        tvDate.setText(date);

        String lang = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).getString(Constants.SHARED_PREF_SELECTED_LANGUAGE, Constants.LANGUAGE_SHORT_CODE_ENGLISH);
        for (ModelCheckboxOption cbOption : checkboxOptions) {
            View view = View.inflate(this, R.layout.layout_checkbox_option, null);
            CheckBox checkBox = view.findViewById(R.id.cbOption);
            final TextInputLayout textInputLayout = view.findViewById(R.id.etOptionLayout);

            String text = cbOption.getText().get(lang);
            text = LibraryTextReplacer.getText(this, text);
            checkBox.setText(text);

            if (cbOption.isChecked()) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    textInputLayout.setVisibility(View.GONE);
                    textInputLayout.setError(null);
                    updateAcceptButton();
                }
            });

            cbOptionList.add(checkBox);
            etOptionLayoutList.add(textInputLayout);

            llWaiverCheckboxOptions.addView(view);
        }

        if (!terminal.getDeviceConfig(this).getEmailSubscriptionCheckboxText().isEmpty()) {
            String emailSubscriptionCheckboxText = terminal.getDeviceConfig(this).getEmailSubscriptionCheckboxText().get(lang);
            cbReceiveEmailNotification.setText(LibraryTextReplacer.getText(this, emailSubscriptionCheckboxText));
        }

        if (!terminal.getDeviceConfig(this).getOverrideWaiverAcceptTitleText().isEmpty()) {
            String overrideWaiverAcceptTitleText = terminal.getDeviceConfig(this).getOverrideWaiverAcceptTitleText().get(lang);
            tvTitle.setText(LibraryTextReplacer.getText(this, overrideWaiverAcceptTitleText));
        }

        if (!terminal.getDeviceConfig(this).getOverrideSignatureConsentText().isEmpty()) {
            String overrideSignatureConsentText = terminal.getDeviceConfig(this).getOverrideSignatureConsentText().get(lang);
            tvElectronicSignatureConsent.setText(LibraryTextReplacer.getText(this, overrideSignatureConsentText));
        }

    }

    private void attachListeners() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollView.getScrollY() == 0) {
                    displayScrollButton();
                } else if (!hidingScrollButton) {
                    hidingScrollButton = true;
                    hideScrollButton();
                }
            }
        });

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    saveImages();
                    processWaiver();
                }
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWaiver();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionCallbacks.getInstance().getWaiverCallback().onWaiverFailure(getString(R.string.cancelled_by_the_customer));
                finish();
            }
        });

        cbElectronicSignatureConsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etElectronicSignatureConsentLayout.setError(null);
                updateAcceptButton();
            }
        });

        cbReceiveEmailNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    session.setReceiveEmailNotification(Constants.STATUS_NOTIFICATION_ENABLED);
                } else {
                    session.setReceiveEmailNotification(Constants.STATUS_NOTIFICATION_DISABLED);
                }
            }
        });

        tvElectronicSignatureConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbElectronicSignatureConsent.performClick();
            }
        });

        scrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(View.FOCUS_DOWN);
                hideScrollButton();
            }
        });

        cbYombuTermsOfUse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etYombuTermsOfUseLayout.setError(null);
                updateAcceptButton();
            }
        });

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbYombuTermsOfUse.performClick();
            }
        });

        tvTermsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent termsAndConditionsActivity = new Intent(ActivityWaiver.this, ActivityTermsAndConditions.class);
                startActivity(termsAndConditionsActivity);
            }
        });

    }

    private void initializeViews() {
        parentLayout = findViewById(R.id.parentLayout);
        rlWaiver = findViewById(R.id.rlWaiver);
        rlProcessing = findViewById(R.id.rlProcessing);
        rlWaiverContent = findViewById(R.id.rlWaiverContent);
        rlInternetProblem = findViewById(R.id.rlInternetProblem);
        rlLoading = findViewById(R.id.rlLoading);
        animatedProcessingView = findViewById(R.id.animatedProcessingView);
        signatureWindow = findViewById(R.id.rlSignature);
        tvTitle = findViewById(R.id.tvTitle);
        tvInstructions = findViewById(R.id.tvInstructions);
        tvClear = findViewById(R.id.tvClear);
        tvName = findViewById(R.id.tvName);
        tvDate = findViewById(R.id.tvDate);
        tvElectronicSignatureConsent = findViewById(R.id.tvElectronicSignatureConsent);
        tvAgree = findViewById(R.id.tvAgree);
        tvTermsOfUse = findViewById(R.id.tvTermsOfUse);
        btnAccept = findViewById(R.id.btnAccept);
        btnRetry = findViewById(R.id.btnRetry);
        btnCancel = findViewById(R.id.btnDecline);
        cbElectronicSignatureConsent = findViewById(R.id.cbElectronicSignatureConsent);
        cbReceiveEmailNotification = findViewById(R.id.cbReceiveEmailNotification);
        cbYombuTermsOfUse = findViewById(R.id.cbYombuTermsOfUse);
        etElectronicSignatureConsentLayout = findViewById(R.id.etElectronicSignatureConsentLayout);
        etSignatureLayout = findViewById(R.id.etSignatureLayout);
        etYombuTermsOfUseLayout = findViewById(R.id.etYombuTermsOfUseLayout);
        scrollView = findViewById(R.id.scrollView);
        scrollButton = findViewById(R.id.scrollButton);
        llWaiverContent = findViewById(R.id.llWaiverContent);
        llWaiverCheckboxOptions = findViewById(R.id.llWaiverCheckboxOptions);
    }

    private void displayScrollButton() {
        scrollButton.setVisibility(View.VISIBLE);
        scrollButton.setAlpha(0.0f);
        scrollButton
                .animate()
                .setDuration(500)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        scrollButton.animate().setListener(null);
                    }
                });
    }

    private void hideScrollButton() {
        hidingScrollButton = true;
        scrollButton
                .animate()
                .setDuration(500)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        scrollButton.setVisibility(View.GONE);
                        scrollButton.animate().setListener(null);
                        hidingScrollButton = false;
                    }
                });
    }

    private void enableSignature() {
        signatureWindow.setBackground(getDrawable(R.drawable.background_border));
        tvInstructions.setTextColor(getColor(R.color.black));

        signatureDrawable = new ViewSignatureDrawable(this);
        signatureWindow.addView(signatureDrawable);

        signatureTimer = new Handler();
        signatureTimer.postDelayed(signatureValidity, 200);

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureDrawable.clear();
                signatureTimer.postDelayed(signatureValidity, 200);
                updateAcceptButton();
            }
        });

        // To disable scrolling page while trying to sign.
        signatureDrawable.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }

    private boolean isFormValid() {
        boolean isValid = true;
        for (int i = 0; i < initialDrawables.size(); i++) {
            if (!initialDrawables.get(i).hasTouches()) {
                isValid = false;
                etInitialLayouts.get(i).setError(getString(R.string.initials_required_message));
                break;
            } else {
                etInitialLayouts.get(i).setError(null);
            }
        }
        if (!signatureDrawable.hasTouches()) {
            isValid = false;
            etSignatureLayout.setError(getString(R.string.signature_required_message));
        } else {
            etSignatureLayout.setError(null);
        }
        if (!cbElectronicSignatureConsent.isChecked()) {
            isValid = false;
            etElectronicSignatureConsentLayout.setError(getString(R.string.electronic_signature_consent_required_message));
        } else {
            etElectronicSignatureConsentLayout.setError(null);
        }
        if (!cbYombuTermsOfUse.isChecked()) {
            isValid = false;
            etYombuTermsOfUseLayout.setError(getString(R.string.agree_with_yombu_terms_required_message));
        } else {
            etYombuTermsOfUseLayout.setError(null);
        }
        return isValid;
    }

    private boolean initialsHasTouches() {
        for (int i = 0; i < initialDrawables.size(); i++) {
            if (!initialDrawables.get(i).hasTouches()) {
                return false;
            }
        }
        return true;
    }

    private void updateAcceptButton() {
        if (signatureDrawable.hasTouches() && cbElectronicSignatureConsent.isChecked() && cbYombuTermsOfUse.isChecked() && initialsHasTouches()) {
            enableAcceptButton();
        } else {
            disableAcceptButton();
        }
    }

    private void disableAcceptButton() {
        btnAccept.setTextColor(ContextCompat.getColor(this, R.color.LightGrey));
    }

    private void enableAcceptButton() {
        btnAccept.setTextColor(ContextCompat.getColor(this, R.color.YombuBlue));
        ObjectAnimator anim = ObjectAnimator.ofFloat(btnAccept, "ScaleY", 0, 1);
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(1000);
        anim.start();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!waiverLoaded) {
            loadWaiver();
        }
    }

    private void loadWaiver() {
        loadingScreen();

        String lang = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).getString(Constants.SHARED_PREF_SELECTED_LANGUAGE, Constants.LANGUAGE_SHORT_CODE_ENGLISH);

        ApiUtils.getWaiverApiService(this).getWaiverTemplate(
                terminal.getDeviceConfig(this).getLocationId(),
                lang
        ).enqueue(new Callback<ModelMyResponse<ModelWaiverTemplate>>() {
            @Override
            public void onResponse(@NonNull Call<ModelMyResponse<ModelWaiverTemplate>> call, @NonNull Response<ModelMyResponse<ModelWaiverTemplate>> response) {
                if (response.isSuccessful()) {
                    updateWaiverContent(response.body().getResult().getBody(), response.body().getResult().getWaiverTemplateId());
                } else {
                    internetProblemScreen();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelMyResponse<ModelWaiverTemplate>> call, @NonNull Throwable t) {
                internetProblemScreen();
            }
        });
    }

    private void updateWaiverContent(String waiverContent, String waiverTemplateId) {
        numberOfInitials = 0;
        llWaiverContent.removeAllViews();

        if (waiverContent != null) {
            String[] waiverTexts = waiverContent.split(Constants.SPLIT_INITIALS_BOX);
            numberOfInitials = waiverTexts.length - 1;

            for (String waiverText : waiverTexts) {
                addWaiverText(waiverText);
                if (numberOfInitials > 0) {
                    addInitialBox();
                    numberOfInitials--;
                }
            }
        }
        session.setWaiverTemplateId(waiverTemplateId);
        enableInitials();
        waiverContentScreen();
        waiverLoaded = true;
    }

    private void addWaiverText(String waiverText) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View waiverTextView = inflater.inflate(R.layout.layout_waiver_text, llWaiverContent, false);
        llWaiverContent.addView(waiverTextView, llWaiverContent.getChildCount());

        waiverText = LibraryTextReplacer.getText(this, waiverText);
        ((TextView) waiverTextView.findViewById(R.id.tvWaiverText)).setText(Html.fromHtml(waiverText));
    }

    private void addInitialBox() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View initialBoxView = inflater.inflate(R.layout.layout_initial_box, llWaiverContent, false);
        llWaiverContent.addView(initialBoxView, llWaiverContent.getChildCount());

        rlInitialBoxes.add((RelativeLayout) initialBoxView.findViewById(R.id.rlInitial));
        tvInitialClears.add((TextView) initialBoxView.findViewById(R.id.tvInitialClear));
        etInitialLayouts.add((TextInputLayout) initialBoxView.findViewById(R.id.etInitialLayout));
    }

    private void enableInitials() {
        for (int i = 0; i < rlInitialBoxes.size(); i++) {
            RelativeLayout initialWindow = rlInitialBoxes.get(i);
            initialWindow.setBackground(getDrawable(R.drawable.background_border));

            final ViewSignatureDrawable initialDrawable = new ViewSignatureDrawable(this);
            initialWindow.addView(initialDrawable);

            final Handler initialTimer = new Handler();
            final int finalI = i;
            final Runnable initialValidity = new Runnable() {
                @Override
                public void run() {
                    if (initialDrawable.hasTouches()) {
                        updateAcceptButton();
                        etInitialLayouts.get(finalI).setError(null);
                    } else {
                        initialTimer.postDelayed(this, 200);
                    }
                }
            };
            initialTimer.postDelayed(initialValidity, 200);

            tvInitialClears.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initialDrawable.clear();
                    initialTimer.postDelayed(initialValidity, 200);
                    updateAcceptButton();
                }
            });

            // To disable scrolling page while trying to sign.
            initialDrawable.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        scrollView.requestDisallowInterceptTouchEvent(false);
                    }
                    return false;
                }
            });

            initialDrawables.add(initialDrawable);
            initialTimers.add(initialTimer);
            initialValidities.add(initialValidity);
        }
    }

    private void loadingScreen() {
        rlWaiverContent.setVisibility(View.GONE);
        rlInternetProblem.setVisibility(View.GONE);
        rlLoading.setVisibility(View.VISIBLE);
    }

    private void internetProblemScreen() {
        rlWaiverContent.setVisibility(View.GONE);
        rlLoading.setVisibility(View.GONE);
        rlInternetProblem.setVisibility(View.VISIBLE);
    }

    private void waiverContentScreen() {
        rlLoading.setVisibility(View.GONE);
        rlInternetProblem.setVisibility(View.GONE);
        rlWaiverContent.setVisibility(View.VISIBLE);
    }

    private void waiverScreen() {
        rlProcessing.setVisibility(View.GONE);
        rlWaiver.setVisibility(View.VISIBLE);
    }

    private void processingScreen() {
        rlWaiver.setVisibility(View.GONE);
        rlProcessing.setVisibility(View.VISIBLE);
    }

    protected void saveImages() {
        StringBuilder signature = new StringBuilder(signatureDrawable.getImage());
        for (int i = 0; i < initialDrawables.size(); i++) {
            signature.append("||").append(initialDrawables.get(i).getImage());
        }
        session.setSignature(signature.toString());
    }

    private void processWaiver() {
        processingScreen();

        String waiverTemplateId = session.getWaiverTemplateId();
        String receiveEmailNotification = session.getReceiveEmailNotification();

        String mindbodyId = session.getMindbodyId();

        String firstName = session.getFirstName();
        String lastName = session.getLastName();
        String phone = session.getPhone();
        String email = session.getEmail();
        String birthday = session.getBirthday();
        String gender = session.getGender();
        String ciNumber = session.getCiNumber();
        String username = session.getUsername();
        String password = session.getPassword();
        String signature = session.getSignature();

        String address = session.getAddress();
        String city = session.getCity();
        String state = session.getState();
        String zip = session.getZip();
        String country = session.getCountry();

        String guardianFirstName = session.getGuardianFirstName();
        String guardianLastName = session.getGuardianLastName();

        String emergencyContactFirstName = session.getEmergencyContactFirstName();
        String emergencyContactLastName = session.getEmergencyContactLastName();
        String emergencyContactPhone = session.getEmergencyContactPhone();

        Map<String, String> minors = new HashMap<>();
        for (int i = 0; i < session.getMinors().size(); i++) {
            minors.put("minors[" + i + "][first_name]", session.getMinors().get(i).getFirstName());
            minors.put("minors[" + i + "][last_name]", session.getMinors().get(i).getLastName());
            minors.put("minors[" + i + "][dob]", session.getMinors().get(i).getDob());
        }

        String hearAboutUsOptions = session.getHearAboutUsOptions();

        ApiUtils.getWaiverApiService(this).processMindbodyWaiver(
                waiverTemplateId,
                receiveEmailNotification,
                mindbodyId,
                firstName,
                lastName,
                phone,
                email,
                birthday,
                gender,
                ciNumber,
                username,
                password,
                signature,
                address,
                city,
                state,
                zip,
                country,
                guardianFirstName,
                guardianLastName,
                emergencyContactFirstName,
                emergencyContactLastName,
                emergencyContactPhone,
                minors,
                hearAboutUsOptions
        ).enqueue(new Callback<ModelMyResponse<ModelWaiverProcess>>() {
            @Override
            public void onResponse(@NonNull Call<ModelMyResponse<ModelWaiverProcess>> call, @NonNull Response<ModelMyResponse<ModelWaiverProcess>> response) {
                waiverScreen();
                if (response.isSuccessful()) {
                    SessionCallbacks.getInstance().getWaiverCallback().onWaiverSuccess();
                    Session.getInstance().reset();
                    close();
                } else {
                    ModelMyErrorResponse errorResponse = null;
                    try {
                        errorResponse = LibraryResponseConverter.convertToErrorResponse(response.errorBody());
                    } catch (Exception ex) {
                    }
                    String errorMessage = getString(R.string.unable_to_process_waiver);
                    if (errorResponse != null && !errorResponse.getErrors().isEmpty()) {
                        errorMessage = errorResponse.getErrors().get(0).getMessage();
                    }
                    SessionCallbacks.getInstance().getWaiverCallback().onWaiverFailure(errorMessage);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelMyResponse<ModelWaiverProcess>> call, @NonNull Throwable t) {
                if (t instanceof TimeoutException) {
                    showRetryWaiverProcessingDialog(call, this);
                } else {
                    SessionCallbacks.getInstance().getWaiverCallback().onWaiverFailure(getString(R.string.something_wrong_try_again));
                    finish();
                }
            }
        });

    }

    protected void showRetryWaiverProcessingDialog(final Call call, final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.YombuAlertDialogError);
        builder.setTitle(R.string.network_timeout);
        builder.setMessage(R.string.check_connection_message);
        builder.setCancelable(false);
        builder.setPositiveButton(
                R.string.RETRY,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        call.clone().enqueue(callback);
                    }
                });
        builder.setNegativeButton(R.string.CANCEL,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionCallbacks.getInstance().getWaiverCallback().onWaiverFailure(getString(R.string.cancelled_by_the_customer));
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
