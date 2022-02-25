package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import novigrad.R;
import novigrad.core.BranchAccount;
import novigrad.core.CustomerAccount;
import novigrad.database.BranchListAdapter;
import novigrad.database.CustomerListAdapter;
import novigrad.database.DatabaseReader;
import novigrad.database.ServiceListAdapter;
import novigrad.services.Service;

public class AccountList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        DatabaseReader dbAccess = new DatabaseReader();
        final ListView accountList = findViewById(R.id.accountList);

        Intent entryIntent = getIntent();
        String listType = entryIntent.getStringExtra("Type");


        dbAccess.setLister(new DatabaseReader.Lister() {
            @Override
            public void listCustomers(ArrayList<CustomerAccount> customerEntries) {
                ListAdapter adapter = new CustomerListAdapter(AccountList.this,customerEntries);
                accountList.setAdapter(adapter);
            }

            @Override
            public void listBranches(ArrayList<BranchAccount> branchEntries) {
                ListAdapter adapter = new BranchListAdapter(AccountList.this,branchEntries);
                accountList.setAdapter(adapter);

            }
        });

        if (listType.equals("Branch")){
            dbAccess.generateBranchList();
        }else if (listType.equals("Customer")){
            dbAccess.generateCustomerList();
        }

    }
}