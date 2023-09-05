package com.example.toufiqul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class dashbord extends AppCompatActivity {
    ImageView img;
    TextView nmae,email;
    Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

       nmae=(TextView)findViewById(R.id.name);
       email=(TextView)findViewById(R.id.emai);

       logoutbtn=(Button)findViewById(R.id.button);

        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        nmae.setText(account.getDisplayName());
        email.setText(account.getEmail());

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}