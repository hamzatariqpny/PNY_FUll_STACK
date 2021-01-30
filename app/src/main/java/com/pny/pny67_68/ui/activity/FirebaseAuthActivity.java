package com.pny.pny67_68.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pny.pny67_68.R;

public class FirebaseAuthActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button registerUser;
    Button loginUser;

    String strEmail, strPassword;

    FirebaseAuth firebaseAuth;

    CallbackManager callbackManager;
    LoginButton fbLoginButton;
    // google login
    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    SignInButton googleSignInButton;

    public static final int RC_SIGN_IN = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        registerUser = findViewById(R.id.registerUser);
        loginUser = findViewById(R.id.loginUser);
        fbLoginButton = findViewById(R.id.login_button);
        googleSignInButton = findViewById(R.id.sign_in_button);

        googleSignInButton.setSize(SignInButton.SIZE_WIDE);

        initFBLogin();
        initGoogleLogin();

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(FirebaseAuthActivity.this, "User canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(FirebaseAuthActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = userEmail.getText().toString();
                strPassword = userPassword.getText().toString();

                if (isFieldNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(FirebaseAuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Log.d("FirebaseAuthActivity", "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                    } else {
                                        Toast.makeText(FirebaseAuthActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strEmail = userEmail.getText().toString();
                strPassword = userPassword.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(FirebaseAuthActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loginUser(task);
                            }
                        });
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginUser(task);
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginUser(task);
                    }
                });
    }

    public void loginUser(Task<AuthResult> task) {

        if (task.isSuccessful()) {
            Log.d("FirebaseAuthActivity", "signInWithEmail:success");
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                String uid = user.getUid();

                SharedPreferences sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid", uid);
                editor.putString("email", user.getEmail());
                editor.apply();

                Intent intent = new Intent(FirebaseAuthActivity.this, FirebaseSendMessageActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);

            }
        } else {
            Toast.makeText(FirebaseAuthActivity.this, "Invalid user name and password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
            Log.d("GoogleSignInAccount","success");
        } catch (ApiException e) {
            Log.d("GoogleSignInAccount", e.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            Intent intent = new Intent(FirebaseAuthActivity.this, FirebaseSendMessageActivity.class);
            intent.putExtra("uid", user.getUid());
            finish();
            startActivity(intent);
        }

    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    void initGoogleLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("333199066816-5r0hli4ve8mi3verh8je0anroad572q7.apps.googleusercontent.com")
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    void initFBLogin() {
        fbLoginButton.setReadPermissions("email", "public_profile");

        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

    }

    public boolean isFieldNotEmpty() {

        String message = "";
        boolean isFieldNotEmpty;

        if (strEmail.isEmpty()) {
            message = "Email is Empty";
            isFieldNotEmpty = false;
        } else if (strPassword.isEmpty()) {
            isFieldNotEmpty = false;
            message = "Password is Empty";
        } else {
            isFieldNotEmpty = true;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        return isFieldNotEmpty;

    }
}