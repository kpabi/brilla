package com.joe.nfortics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;


public class User extends RealmObject  {

    @PrimaryKey
    public String id;

    public String firstName;
    public String lastName;
    public Date dateOfBirth;
    public String address;
    public String accountNumber;

    //used to return  date of birth as calendar in getDateOfBirth without creating new calendar objects everytime
    @Ignore
    private Calendar placeholderCalendar;


    public int getAge(){
        return getAge(dateOfBirth);
    }
    public int getAge(Date date){
        if(date==null){
            return 0;
        }
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        Calendar currentDate = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        currentDate.add(Calendar.YEAR,-year);
        currentDate.add(Calendar.MONTH,-month);
        int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
        currentDate.add(Calendar.DAY_OF_MONTH,-day);
        return currentDate.get(Calendar.YEAR);



    }



    public void setDateOfBirth(Calendar calendar){
        if(calendar==null){
            dateOfBirth =null;
            return;
        }

        if(System.currentTimeMillis()<=calendar.getTimeInMillis()){
            throw new IllegalArgumentException("date cant be later than today");
        }
        this.dateOfBirth = calendar.getTime();

    }

    public Date getDateOfBirth() {
        if(placeholderCalendar==null){
            placeholderCalendar = Calendar.getInstance();
        }
        placeholderCalendar.setTime(dateOfBirth);
        return dateOfBirth;
    }

    public String getFormattedDateOfBirth(){


        if(dateOfBirth==null){
            return "";
        }
        return SimpleDateFormat.getDateInstance().format(dateOfBirth);
    }

    public void setAccountNumber(String firstName,String lastName){
        String acc = "";
        if(firstName.length()<3){
            acc+=firstName;
        }
        else {
            acc+=firstName.substring(0,3);
        }

        Random random = new Random();
        for (int i=0;i<5;i++){
            acc+=random.nextInt(10);
        }

        if(lastName.length()<3){
            acc+=lastName;
        }
        else {
            acc+=lastName.substring(lastName.length()-3);
        }

        accountNumber = acc;

    }













}
