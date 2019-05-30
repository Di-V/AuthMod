package app.di_v.authmod;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity for login user
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mBtnAuth;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);

        // Buttons
        mBtnAuth = findViewById(R.id.btn_login);
        mBtnAuth.setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_login) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!isEmailValid() & !isPasswordValid()) {
            return;
        }

        showProgressDialog(true);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithEmail:success");

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            LoginActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            showProgressDialog(false);
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showProgressDialog(boolean chek) {

        ProgressBar progress_auth = findViewById(R.id.progressBar_login);

        if (chek) {
            mBtnAuth.setVisibility(View.GONE);
            progress_auth.setVisibility(View.VISIBLE);
        } else {
            progress_auth.setVisibility(View.GONE);
            mBtnAuth.setVisibility(View.VISIBLE);
        }
    }

    private boolean isEmailValid() {

        if (!mEmailField.toString().contains("@") & mEmailField.length() < 7) {
            mEmailField.setError("Invalid Email");
            return false;
        } else {
            mEmailField.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid() {

        if (mPasswordField.length() < 6) {
            mPasswordField.setError("Invalid Password");
            return false;
        } else {
            mPasswordField.setError(null);
            return true;
        }
    }
}
