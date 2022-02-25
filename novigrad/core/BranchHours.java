package novigrad.core;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import novigrad.R;
import novigrad.database.DatabaseReader;
import novigrad.database.DatabaseWriter;
import novigrad.ui.BranchHoursDialog;

public class BranchHours extends AppCompatActivity implements BranchHoursDialog.DialogListener {
    TextView mon, tue, wed, thurs, fri, sat, sun, current,addr;
    Button addrBtn;
    String day;
    String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReader dbReader = new DatabaseReader();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_hours);
        mon = (TextView) findViewById(R.id.monTime);
        tue = (TextView) findViewById(R.id.tueTime);
        wed = (TextView) findViewById(R.id.wedTime);
        thurs = (TextView) findViewById(R.id.thursTime);
        fri = (TextView) findViewById(R.id.friTime);
        sat = (TextView) findViewById(R.id.satTime);
        sun = (TextView) findViewById(R.id.sunTime);




        dbReader.setHourLister(new DatabaseReader.Hours() {
            @Override
            public void getHours(String hours, String day) {
                switch (day){
                    case"Monday":{
                        mon.setText(hours);
                    }
                    case "Tuesday":{
                        tue.setText(hours);

                    }
                    case "Wednesday":{
                        wed.setText(hours);

                    }
                    case "Thursday":{
                        thurs.setText(hours);

                    }
                    case "Friday":{
                        fri.setText(hours);

                    }
                    case "Saturday":{
                        sat.setText(hours);

                    }
                    case "Sunday":{
                        sun.setText(hours);

                    }
                }
            }

        });
        for (String checkDays : days){
            dbReader.getHours(getIntent().getStringExtra("Account Name"),checkDays);
        }


        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = mon; //using current to keep track of which text to modify
                day = "Monday";
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = tue;
                day = "Tuesday";
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = wed;
                day = "Wednesday";
            }
        });

        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = thurs;
                day = "Thursday";
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = fri;
                day = "Friday";
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = sat;
                day = "Saturday";
            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                current = sun;
                day = "Sunday";
            }
        });


    }

    public void openDialog(){
        BranchHoursDialog branchHoursDialog = new BranchHoursDialog();
        branchHoursDialog.show(getSupportFragmentManager(), "Test");
    }


    public boolean validateInputs(String aTime){
        String[] inputValue = aTime.split(":");
        //Checking to make sure we have a colon separating the two times
        if (aTime.indexOf(':') == -1){
            return false;
        }
        //Checking that the length is 1 or 2 (could have simplified this)
        if (inputValue[0].length() > 2 || inputValue[0].length() < 1){
            return false;
        }
        //See above
        if (inputValue[1].length() > 2 || inputValue[1].length() < 1){
            return false;
        }
        //Checking that the characters separated by colons are only numbers
        if (inputValue[0].matches("[a-zA-Z]+") || inputValue[1].matches("[a-zA-Z]+")){
            return false;
        }
        //Checking that the values are constrained to 0-23 for hours and 0-59 for minutes
        int hourCheck = Integer.parseInt(inputValue[0]);
        int minuteCheck = Integer.parseInt(inputValue[1]);
        if (hourCheck >= 24 || hourCheck < 0 || minuteCheck >= 60 || minuteCheck < 0){
            return false;
        }
        return true;
    }

    public String prefixZero(String aTime){
        //if one of the inputs is a single digit, will add a 0 for appearance.
        if (aTime.length() == 1){
            return "0" + aTime;
        }else{
            return (aTime);
        }
    }

    @Override
    public void applyTexts(String openTime, String closeTime) {
        DatabaseWriter dbWriter = new DatabaseWriter();

//        System.out.println("Apply Texts");
//        System.out.println(openTime);
//        System.out.println(closeTime);

        //removing all whitespace
        openTime = openTime.replaceAll("\\s+","");
        closeTime = closeTime.replaceAll("\\s+","");

        if (openTime.equals("24") || closeTime.equals("24")) {
            current.setText("CLOSED");
            dbWriter.setHours(getIntent().getStringExtra("Account Name"), "CLOSED", day);
            return;
        }

        if (!validateInputs(openTime) || !validateInputs(closeTime)){
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid time. \nFormat is Hour(0-23):Minute(0-59)", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        //Checking that the opening time isn't after the closing time.
        String[] openingTimes = openTime.split(":");
        String[] closingTimes = closeTime.split(":");
        int openingHour, openingMinute, closingHour, closingMinute;
        openingHour = Integer.parseInt(openingTimes[0]);
        openingMinute = Integer.parseInt(openingTimes[1]);
        closingHour = Integer.parseInt(closingTimes[0]);
        closingMinute = Integer.parseInt(closingTimes[1]);
        if (openingHour > closingHour || (openingHour == closingHour && openingMinute >= closingMinute)){
            Toast toast = Toast.makeText(getApplicationContext(), "Opening time must be before closing.", Toast.LENGTH_LONG);
            toast.show();
        }else{
            //Checking to see if a 0 needs to be added
            openingTimes[0] = prefixZero(openingTimes[0]);
            openingTimes[1] = prefixZero(openingTimes[1]);
            closingTimes[0] = prefixZero(closingTimes[0]);
            closingTimes[1] = prefixZero(closingTimes[1]);

            openTime = openingTimes[0]+":"+openingTimes[1];
            closeTime = closingTimes[0]+":"+closingTimes[1];
            current.setText(openTime + " - " + closeTime);
            dbWriter.setHours(getIntent().getStringExtra("Account Name"),openTime + " - " + closeTime,day);
        }
    }
}