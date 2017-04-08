package com.joe.nfortics;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.firstName)TextView firstNameTxt;
    @BindView(R.id.lastName)TextView lastNameTxt;
    @BindView(R.id.dateOfBirthTv)TextView dateOfBirthTxt;
    @BindView(R.id.address) TextView addressTxt;

    Calendar dateOfBirth;

    User user;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                if(dateOfBirth==null){
                    dateOfBirth = Calendar.getInstance();
                }
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfBirth.set(Calendar.YEAR,year);
                        dateOfBirth.set(Calendar.MONTH,month);
                        dateOfBirth.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        onDateOfBirthChanged();


                    }
                },dateOfBirth.get(Calendar.YEAR),dateOfBirth.get(Calendar.MONTH),dateOfBirth.get(Calendar.DAY_OF_MONTH));
                return dialog;
            default:return null;
        }
    }

    @OnClick(R.id.dateOfBirthTv)
    void pickDateOFBirth(){
        showDialog(0);
    }
    @OnClick(R.id.save)
    void save(){


        if(TextUtils.isEmpty(firstNameTxt.getText())){
            Toast.makeText(this, "firstname cant be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lastNameTxt.getText())){
            Toast.makeText(this, "lastname cant be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(user.dateOfBirth==null){
            Toast.makeText(this, "please set date", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(addressTxt.getText())){
            Toast.makeText(this, "address must be set", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            user.setDateOfBirth(dateOfBirth);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }



        user.firstName = firstNameTxt.getText().toString();
        user.lastName = lastNameTxt.getText().toString();
        user.address = addressTxt.getText().toString();

        user.id = UUID.randomUUID().toString();

        user.setAccountNumber(user.firstName,user.lastName);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {

            User managedUser =  realm.copyToRealmOrUpdate(user);


            realm.commitTransaction();
        }
        catch (Exception e){
            realm.cancelTransaction();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;

        }
        Intent i = new Intent(this,UserDetails.class);
        i.putExtra("id",user.id);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        user = new User();
//        user = ((App)getApplication()).getUser();
    }
    void onDateOfBirthChanged(){
        user.setDateOfBirth(dateOfBirth);
        dateOfBirthTxt.setText(user.getFormattedDateOfBirth());

    }



//    ObservableOnSubscribe<Calendar> dateOfBirthEmitter = new ObservableOnSubscribe<Calendar>() {
//        @Override
//        public void subscribe(final ObservableEmitter<Calendar> dateOfBirthEmitter) throws Exception {
//
//        }
//    };
//    Observable<Calendar> dateOfBirthChanged = Observable.create(dateOfBirthEmitter);


}
