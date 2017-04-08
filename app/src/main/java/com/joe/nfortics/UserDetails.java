package com.joe.nfortics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class UserDetails extends AppCompatActivity {

    @BindView(R.id.name)TextView nameTv;
    @BindView(R.id.dateOfBirth)TextView dateOfBirthTv;
    @BindView(R.id.age)TextView ageTv;
    @BindView(R.id.accountNumber)TextView accountNumberTxt;
    @OnClick(R.id.ok)
    void newInput(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        user = ((App)getApplication()).getUser();
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        if(id==null){
            Toast.makeText(this, "error fetching user", Toast.LENGTH_SHORT).show();

//            return;
        }
        Realm realm = Realm.getDefaultInstance();
        if(id==null){
            user = realm.where(User.class).findFirst();
        }
        else {
            user = realm.where(User.class).equalTo("id",id).findFirst();
        }


        if(user==null){
            Toast.makeText(this, "user not found", Toast.LENGTH_SHORT).show();
            return;
        }
        nameTv.setText("name :"+user.firstName+" "+user.lastName);
        ageTv.setText("age :"+user.getAge());
        dateOfBirthTv.setText("date of birth :"+user.getFormattedDateOfBirth());
        accountNumberTxt.setText("account number :"+user.accountNumber);
    }
}
