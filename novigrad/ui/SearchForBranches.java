package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import novigrad.R;
import novigrad.core.BranchAccount;
import novigrad.core.BranchHours;
import novigrad.core.CustomerAccount;
import novigrad.database.BranchListAdapter;
import novigrad.database.CustomerListAdapter;
import novigrad.database.DatabaseReader;
import novigrad.services.Service;
import novigrad.database.ServiceListAdapter;

import static android.widget.Toast.LENGTH_LONG;

public class SearchForBranches extends AppCompatActivity implements SearchByHours.SearchHoursListener, SearchByAddress.SearchAddressListener {
    Button searchHours, searchAddress, rating;
    ListView branchesFound;
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_branches);

        branchesFound = (ListView) findViewById(R.id.dbFound);
        searchHours = (Button) findViewById(R.id.hours);
        searchAddress = (Button) findViewById(R.id.address);
        rating = (Button) findViewById(R.id.flex);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchForBranches.this, BranchRate.class);
                intent.putExtra("Role", "Customer");
                startActivity(intent);
            }
        });
        DatabaseReader dbAccess = new DatabaseReader();
        dbAccess.setLister(new DatabaseReader.Lister() {
            @Override
            public void listCustomers(ArrayList<CustomerAccount> customerEntries) {
                ;
            }

            @Override
            public void listBranches(final ArrayList<BranchAccount> branchEntries) {
                final ListAdapter adapter = new BranchListAdapter(SearchForBranches.this, branchEntries);
                branchesFound.setAdapter(adapter);
            }
        });
        dbAccess.generateBranchList();
        searchHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHoursDialog();
            }
        });

        searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddressDialog();
            }
        });


    }

    public void openHoursDialog() {
        SearchByHours searchByHours = new SearchByHours();
        searchByHours.show(getSupportFragmentManager(), "test");
    }

    public void openAddressDialog() {
        SearchByAddress searchByAddress = new SearchByAddress();
        searchByAddress.show(getSupportFragmentManager(), "test");
    }

    public boolean validateHourInputs(String aTime) {
        String[] inputValue = aTime.split(":");
        //Checking to make sure we have a colon separating the two times
        if (aTime.indexOf(':') == -1) {
            return false;
        }
        //Checking that the length is 1 or 2 (could have simplified this)
        if (inputValue[0].length() > 2 || inputValue[0].length() < 1) {
            return false;
        }
        //See above
        if (inputValue[1].length() > 2 || inputValue[1].length() < 1) {
            return false;
        }
        //Checking that the characters separated by colons are only numbers
        if (inputValue[0].matches("[a-zA-Z]+") || inputValue[1].matches("[a-zA-Z]+")) {
            return false;
        }
        //Checking that the values are constrained to 0-23 for hours and 0-59 for minutes
        int hourCheck = Integer.parseInt(inputValue[0]);
        int minuteCheck = Integer.parseInt(inputValue[1]);
        if (hourCheck >= 24 || hourCheck < 0 || minuteCheck >= 60 || minuteCheck < 0) {
            return false;
        }
        return true;
    }

    public String prefixZero(String aTime) {
        //if one of the inputs is a single digit, will add a 0 for appearance.
        if (aTime.length() == 1) {
            return "0" + aTime;
        } else {
            return (aTime);
        }
    }

    @Override
    public void applyTextsHours(final String desiredHours) {
        final ArrayList<BranchAccount> currentAccounts = new ArrayList<BranchAccount>();
        final DatabaseReader dbAccess = new DatabaseReader();
        dbAccess.setLister(new DatabaseReader.Lister() {
            @Override
            public void listCustomers(ArrayList<CustomerAccount> customerEntries) {
                ;
            }

            @Override
            public void listBranches(final ArrayList<BranchAccount> branchEntries) {
                final ArrayList<BranchAccount> branchNames = new ArrayList<BranchAccount>();
                for (int i = 0; i < branchEntries.size(); i++) {
                    System.out.println(desiredHours);
                    final ListAdapter adapter = new BranchListAdapter(SearchForBranches.this, branchNames);
                    final ArrayList<String> suitable = new ArrayList<String>();
                    DatabaseReader dbReader = new DatabaseReader();
                    final int finalI = i;
                    dbReader.setHourLister(new DatabaseReader.Hours() {
                        @Override
                        public void getHours(String hours, String day) {
                            switch (day) {
                                case "Monday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));
                                        }
                                    }
                                }
                                case "Tuesday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));

                                        }
                                    }
                                }
                                case "Wednesday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));

                                        }
                                    }
                                }
                                case "Thursday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));

                                        }
                                    }
                                }
                                case "Friday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));

                                        }
                                    }
                                }
                                case "Saturday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));

                                        }
                                    }
                                }
                                case "Sunday": {
                                    if (hours != null) {
                                        if (hours.substring(hours.length() - 5).equals(desiredHours)) {
                                            branchNames.add(branchEntries.get(finalI));
                                        }
                                    }
                                }
                            }
                            System.out.println("Q C+ " + branchNames.size());

                        }

                    });
                    for (String checkDays : days) {
                        dbReader.getHours(branchEntries.get(i).getEmail(), checkDays);
                    }
                    branchesFound.setAdapter(adapter);
                }

                //Toast toast = Toast.makeText(getApplicationContext(), "testing", Toast.LENGTH_LONG);
                //toast.show();
//                try{
//                    dbAccess.getHours(branchEntries.get(3).getEmail(), "Monday");
//                }catch(Exception e){
//                    System.out.println(e);
//                }
            }
        });

        dbAccess.generateBranchList();
    }


    @Override
    public void applyTextAddress(final String desiredAddress) {
        System.out.println("1");
        final ArrayList<BranchAccount> currentAccounts = new ArrayList<BranchAccount>();
        final DatabaseReader dbAccess = new DatabaseReader();
        dbAccess.setLister(new DatabaseReader.Lister() {
            @Override
            public void listCustomers(ArrayList<CustomerAccount> customerEntries) {
                ;
            }

            @Override
            public void listBranches(final ArrayList<BranchAccount> branchEntries) {
                System.out.println("3");
                final ArrayList<BranchAccount> branchNames = new ArrayList<BranchAccount>();
                for (int i = 0; i < branchEntries.size(); i++) {
                    try {
                        if (branchEntries.get(i).getAddress().toLowerCase().contains(desiredAddress.toLowerCase())) {
                            currentAccounts.add(branchEntries.get(i));
                        }
                    } catch (NullPointerException ignored) {
                    }

                    final ListAdapter adapter = new BranchListAdapter(SearchForBranches.this, currentAccounts);
                    branchesFound.setAdapter(adapter);
                }
            }
        });
        dbAccess.generateBranchList();
        //Now we populate listview with database content, on top of input validation
        Toast toast = Toast.makeText(getApplicationContext(), "Testing: " + desiredAddress, LENGTH_LONG);
        toast.show();
    }
}