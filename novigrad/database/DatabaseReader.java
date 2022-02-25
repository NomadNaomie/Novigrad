package novigrad.database;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import novigrad.core.AccountState;
import novigrad.core.BranchAccount;
import novigrad.core.CustomerAccount;
import novigrad.services.Service;
import novigrad.services.ServiceRequest;

/**
 *
 * Database Reader class
 * A DatabaseReader object will perform read-only operations on the database, it features a listener
 * interface to communicate account states for login to the UI Thread without blocking it.
 */
public class DatabaseReader {



    /**
     * Listener Interface, defines a statusReady method that communicates the AccountState to the main UI
     */
    public interface Listener{
        void statusReady(AccountState state);
    }
    public interface Lister{
        void listCustomers(ArrayList<CustomerAccount> customerEntries);
        void listBranches(ArrayList<BranchAccount> branchEntries);
    }
    public interface Hours{
        void getHours(String hours, String day);
    }
    public interface Services{
        void getServices(ArrayList<String> services);
    }
    public interface BranchServices{
        void getBranchServices(ArrayList<String> services);
    }
    public interface ServiceRequests{
        void getRequests(ArrayList<ServiceRequest> requests);
    }
    private BranchServices branchServices = null;
    private Listener listener = null;
    private Lister lister = null;
    private Hours hourLister = null;
    private Services services = null;
    private ServiceRequests requests = null;
    private FirebaseDatabase masterTable;
    private DatabaseReference tableReference;
    public void setListener(Listener listener){
        this.listener = listener;
    }
    public void setServiceRequests(ServiceRequests serviceRequests){this.requests = serviceRequests;}
    public void setLister(Lister lister){this.lister = lister;}
    public void setHourLister(Hours hourLister){this.hourLister = hourLister;}
    public void getServices(Services services){this.services=services;}
    public void getBranchServices(BranchServices branchServices){this.branchServices = branchServices;}



    /**
     * This method receives a CustomerAccount object and checks whether the email is held in our DB
     * Based on the results it updates the listener interface
     * @param customerAccount
     */
    public void checkExistingCustomerAccount(final CustomerAccount customerAccount){
        masterTable = FirebaseDatabase.getInstance(); //Master DB
        tableReference = masterTable.getReference("users/Customer").child(customerAccount.getEmail()); //Gets the path master/users/customers/{customer email}
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                if (snap.exists()) { //If there is an entry with that email
                    CustomerAccount retrievedAccount = snap.getValue(CustomerAccount.class); //import its values to a CustomerAccount object
                    if (retrievedAccount.getPass().equals(customerAccount.getPass())) { //Gets the password, should probably hash this, if the passwords are the same then it is this persons account
                        listener.statusReady(AccountState.LOGGEDIN); //Sets the listener to Logged In
                    } else { //Otherwise, the account exists but password is wrong.
                        listener.statusReady(AccountState.EXISTS);
                    }
                }else{ //Otherwise the account doesn't actually exist.
                    listener.statusReady(AccountState.DOESNOTEXIST);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void superuser() {
        listener.statusReady(AccountState.LOGGEDIN);
    }
    /**
     * This method receives a BranchAccount object and checks whether the email is held in our DB
     * Based on the results it updates the listener interface
     * @param branchAccount
     */
    public void checkExistingBranchAccount(final BranchAccount branchAccount) {
        //See above.
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Branch").child(branchAccount.getEmail());
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                if (snap.exists()) {
                    BranchAccount retrievedAccount = snap.getValue(BranchAccount.class);
                    if (retrievedAccount.getPass().equals(branchAccount.getPass())) {
                        listener.statusReady(AccountState.LOGGEDIN);
                    } else {
                        listener.statusReady(AccountState.EXISTS);
                    }
                }else{
                    listener.statusReady(AccountState.DOESNOTEXIST);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void generateBranchList(){
        final ArrayList<BranchAccount> branchEntries = new ArrayList<>();
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Branch");
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    branchEntries.add(snap.getValue(BranchAccount.class));
                    System.out.println(branchEntries.get(0).getName());

                }
                lister.listBranches(branchEntries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void generateCustomerList(){
        final ArrayList<CustomerAccount> customerEntries =new ArrayList<CustomerAccount>();
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Customer");
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    customerEntries.add(snap.getValue(CustomerAccount.class));
                    System.out.println(customerEntries.size());
                }
                lister.listCustomers(customerEntries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getHours(String name, final String day){
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("hours").child(name).child(day);
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                hourLister.getHours(snap.getValue(String.class), day);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getServicesOfBranch(String name){
        final ArrayList<String> servicesAvailable = new ArrayList<String>();
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("users/Branch").child(name).child("services");
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dSnap) {
                for (DataSnapshot snap : dSnap.getChildren()) {
                    servicesAvailable.add(snap.getValue(String.class));
                }
                branchServices.getBranchServices(servicesAvailable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getAvailableServices(){
        final ArrayList<String> ServicesAvailable = new ArrayList<>();
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("services");
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    ServicesAvailable.add(snap.getValue(String.class));

                }
                services.getServices(ServicesAvailable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getServiceRequests(String name){
        final ArrayList<ServiceRequest> Requests = new ArrayList<>();
        masterTable = FirebaseDatabase.getInstance();
        tableReference = masterTable.getReference("Service Requests").child(name);
        tableReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Requests.add(snap.getValue(ServiceRequest.class));

                }
                requests.getRequests(Requests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
