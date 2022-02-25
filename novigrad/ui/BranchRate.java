package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import novigrad.R;
import novigrad.core.BranchAccount;
import novigrad.core.CustomerAccount;
import novigrad.database.BranchListAdapter;
import novigrad.database.BranchListRateAdapter;
import novigrad.database.CustomerListAdapter;
import novigrad.database.DatabaseReader;
import novigrad.database.DatabaseWriter;
import novigrad.database.ServiceListAdapter;
import novigrad.services.Service;

public class BranchRate extends AppCompatActivity {


    DatabaseReader dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_rate);
        dbAccess = new DatabaseReader();
        final ListView branchList = findViewById(R.id.branchList);

        dbAccess.setLister(new DatabaseReader.Lister() {
            @Override
            public void listCustomers(ArrayList<CustomerAccount> customerEntries) {
                ListAdapter adapter = new CustomerListAdapter(BranchRate.this,customerEntries);
                branchList.setAdapter(adapter);
            }

            @Override
            public void listBranches(ArrayList<BranchAccount> branchEntries) {
                ListAdapter adapter = new BranchListRateAdapter(BranchRate.this,branchEntries);
                branchList.setAdapter(adapter);

            }
        });
        dbAccess.generateBranchList();

    }
}