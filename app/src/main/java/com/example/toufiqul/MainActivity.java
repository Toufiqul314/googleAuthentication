package com.example.toufiqul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    SignInButton btn;
    GoogleSignInClient mGoogleSingInClient;
    private FirebaseAuth mauth;
    public void onStart(){
        super.onStart();
        FirebaseUser currentuser=mauth.getCurrentUser();
        if(currentuser!=null){
            startActivity(new Intent(getApplicationContext(),dashbord.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);//title hide
//        getSupportActionBar().hide();//title bar hide
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullScreen

        //action bar logo with title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.baseline_home_24);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Logo App");

        setContentView(R.layout.activity_main);
processrequest();
        
        btn=(SignInButton)findViewById(R.id.sbtn);
        mauth=FirebaseAuth.getInstance();
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        progcesslogIn();
    }
});
    }


    private void processrequest() {
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
mGoogleSingInClient=GoogleSignIn.getClient(this,gso);
    }

    private void progcesslogIn() {
        Intent signIntent=mGoogleSingInClient.getSignInIntent();
        startActivityForResult(signIntent,1000);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
     super.onActivityResult(requestCode,resultCode,data);
     if(requestCode==1000){
         Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
try {
    GoogleSignInAccount account=task.getResult(ApiException.class);
    firebaseAuthGoogle(account.getIdToken());
}catch (ApiException e){
    Toast.makeText(getApplicationContext(), "Error is gettting google information", Toast.LENGTH_SHORT).show();
}
     }
    }

    private void firebaseAuthGoogle(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mauth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),dashbord.class));
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Error in getting Google's information", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}