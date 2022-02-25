package novigrad.database;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import novigrad.core.AccountState;
import novigrad.core.AdminAccount;
import novigrad.core.BranchAccount;
import novigrad.core.CustomerAccount;
import novigrad.services.Service;
import novigrad.services.ServiceRequest;

/**
 * Database writer class,
 *
 * customerAccountEntry() creates OR overwrites a CustomerAccount object's primitive types to the DB
 * branchAccountEntry() creates OR overwrites a BranchAccount object's primitive types to the DB
 * adminAccountEntry() creates OR overwrites a AdminAccount object's primitive types to the DB
 */

public class DatabaseWriter {
    FirebaseDatabase masterTable; //DB
    DatabaseReference tableReference; //DB Reference

    /**
     * Takes in a customerAccount and writes it to the DB under the header of it's email address
     * @param customerAccount
     *
     */
    public void customerAccountEntry(CustomerAccount customerAccount){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users");
        tableReference.child("Customer").child(customerAccount.getEmail()).setValue(customerAccount);
        statusBroadcast(AccountState.EXISTS);
    }

    public void addAddress(String name, String Address){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Branch").child(name).child("Address");
        tableReference.setValue(Address);
    }

    /**
     * Takes in a branchAccount and writes it to the DB under the header of it's email address
     * @param branchAccount
     */
    public void branchAccountEntry(BranchAccount branchAccount){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users");
        tableReference.child("Branch").child(branchAccount.getEmail()).setValue(branchAccount);
        statusBroadcast(AccountState.EXISTS);
    }

    /**
     * takes in adminAccount and writes it to the DB under the header of the name.
     * @param adminAccount
     */
    public void adminAccountEntry(AdminAccount adminAccount){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users");
        tableReference.child("Admin").child(adminAccount.getName()).setValue(adminAccount);
        statusBroadcast(AccountState.EXISTS);
    }
    private void statusBroadcast(AccountState state){
        Intent intent = new Intent("create");
        intent.putExtra("state",state);
    }

    public void serviceEntry(String service){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("services");
        tableReference.child(service).setValue(service);
    }

    public void deleteService(String name){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("services").child(name);
        tableReference.removeValue();
    }

    public void setHours(String name, String hours, String day){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("hours").child(name).child(day);
        tableReference.setValue(hours);
    }
    public void addService(String name, String service){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Branch").child(name).child("services").child(service);
        tableReference.setValue(service);
    }

    public void removeService(String name, String service) {
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Branch").child(name).child("services").child(service);
        tableReference.removeValue();
    }

    public void serviceRequest(ServiceRequest request, String name){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("Service Requests").child(name).push();
        String id = tableReference.getKey();
        request.setId(id);
        tableReference.setValue(request);
    }
    public void deleteRequest(String id, String name){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("Service Requests").child(name).child(id);
        tableReference.removeValue();
    }

    public void addRating(String name, float rating){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("Ratings").child(name).push();
        tableReference.setValue(rating);
    }
}
