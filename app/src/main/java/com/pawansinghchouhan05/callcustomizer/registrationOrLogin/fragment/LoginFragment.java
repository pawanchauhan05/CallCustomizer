package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.activity.HomeActivity;
//import com.pawansinghchouhan05.callcustomizer.home.activity.HomeActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener  {

    private static final String TAG = "LoginFragment";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    public static CallbackManager callbackManager = CallbackManager.Factory.create();
    private Validator logInvalidator;

    @Email
    @ViewById(R.id.editUserEmail)
    EditText editUserEmail;

    @NotEmpty
    @ViewById(R.id.editPassword)
    EditText editPassword;

    @ViewById(R.id.sign_in_button)
    SignInButton mSignInButton;

    @AfterViews
    void init() {

        logInvalidator = new Validator(this);
        FacebookSdk.sdkInitialize(getContext());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        logInvalidator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                Utils.savePreferences(getContext(), Constant.LOGIN_TYPE, Constant.LOGIN_TYPE_AUTH);
                Utils.savePreferences(getContext(), Constant.LOGIN_STATUS, Constant.LOGIN_STATUS_VALUE);
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getContext());
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Click(R.id.fbLogin)
    void onFacebookLogin() {
        List<String> permissionNeeds = Arrays.asList("email");

        LoginManager.getInstance().logInWithReadPermissions(
                this,
                permissionNeeds);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResults) {
                        Log.e("sucess","success");
                        Utils.savePreferences(getContext(), Constant.LOGIN_TYPE, Constant.LOGIN_TYPE_FACEBOOK);
                    }

                    @Override
                    public void onCancel() {
                        Log.e("sucess","cancel");
                    }


                    @Override
                    public void onError(FacebookException e) {
                        Log.e("sucess","error");
                    }
                });

    }

    private void handleFirebaseAuthResult(AuthResult authResult) {
        if (authResult != null) {
            // Welcome the user
            FirebaseUser user = authResult.getUser();
            Toast.makeText(getContext(), "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

            // Go back to the main activity
            startActivity(new Intent(getContext(), HomeActivity.class));
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getContext(), HomeActivity.class));
                            getActivity().finish();
                        }
                    }
                });
    }

    @Click(R.id.buttonRegisterUser)
    void loadRegistrationFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.registrationOrLoginContainer, new RegistrationFragment_());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Click(R.id.textViewForgotPassword)
    void forgotPassword() {
        final Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_for_forgot_password);
        dialog.show();
    }


    @Click(R.id.sign_in_button)
    void googleLogin() {
        Utils.savePreferences(getContext(), Constant.LOGIN_TYPE, Constant.LOGIN_TYPE_GOOGLE);
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            default:
                return;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.loginButton)
    void simplelogin() {
        logInvalidator.validate();
    }


}
