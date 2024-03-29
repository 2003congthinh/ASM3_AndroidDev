package myapk.asm3;
// Gmail authentication are from google identity document pages: https://developers.google.com/identity/one-tap/android/create-new-accounts
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class Login extends AppCompatActivity {
    private String userEmail="";
    private String userPassword="";
    private String status ="";
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        requestPermissions();

        // Gmail login function
        oneTapClient = Identity.getSignInClient(this);
//        oneTapClient = Identity.getSignInClient(this, CredentialsOptions.builder().forceEnableSaveDialog().build());
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        ActivityResultLauncher<IntentSenderRequest> intentSenderRequestActivityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                if (idToken !=  null) {
                                    // Got an ID token from Google. Use it to authenticate
                                    // with your backend.
                                    userEmail = credential.getId();
                                    Toast.makeText(Login.this, userEmail, Toast.LENGTH_SHORT).show();

                                    // Authentication
                                    new PostAccountWithGmail().execute();
//                                    Intent intent = new Intent(Login.this, HomeScreen.class);
//                                    startActivity(intent);

                                    Log.d("TAG", "Got ID token.");
                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        CardView gmailBtn = findViewById(R.id.gmailBtn);
        gmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(Login.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
//                                    startIntentSenderForResult(
//                                            result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
//                                            null, 0, 0, 0);
//                                Toast.makeText(getApplicationContext(), "oke", Toast.LENGTH_SHORT).show();
                                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                intentSenderRequestActivityResultLauncher.launch(intentSenderRequest);
                            }
                        })
                        .addOnFailureListener(Login.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // No Google Accounts found. Just continue presenting the signed-out UI.
                                Log.d("TAG", e.getLocalizedMessage());
                                new AlertDialog.Builder(Login.this)
                                        .setTitle("Sign-In Failed")
                                        .setMessage("There was an error during the sign-in process. Please try again later.")
                                        .setPositiveButton("OK", null)
                                        .show();
//                                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    // Request permissions
    private void requestPermissions(){
        if (ActivityCompat.checkSelfPermission(Login.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Login.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.SEND_SMS,
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.READ_CALL_LOG}, MY_PERMISSIONS_REQUEST);
        }
    }

    public void goToHome(View view) {
        TextView emailText = findViewById(R.id.email);
        userEmail = emailText.getText().toString();
        TextView passwordText = findViewById(R.id.password);
        userPassword = passwordText.getText().toString();
        new PostAccount().execute();
    }
    //    POST DATA
    private class PostAccount extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            status = HttpHandler.postRequestLogin(userEmail,userPassword);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Login", "Status: " + status);
            if(status.equals("Success: OK")){
                Toast.makeText(Login.this, status, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, HomeScreen.class);
                intent.putExtra("uemail", userEmail);
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Something's wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PostAccountWithGmail extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            status = HttpHandler.postRequestLoginByGmail(userEmail);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Login", "Status: " + status);
            if(status.equals("Success: OK")){
                Toast.makeText(Login.this, status, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, HomeScreen.class);
                intent.putExtra("uemail", userEmail);
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "This is a new account", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Interests.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        }
    }

    public void goToSignup(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
        finish();
    }
}