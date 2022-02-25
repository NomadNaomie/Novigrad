 package novigrad.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

import novigrad.ui.BranchRequestsPage;
import novigrad.core.BranchAccount;

public class BranchListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BranchAccount> branches;

    public BranchListAdapter(Context context, ArrayList<BranchAccount> branches) {
        this.context = context;
        this.branches = branches;
    }

    @Override
    public int getCount() {
        return branches.size();
    }

    @Override
    public Object getItem(int position) {
        return branches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

//    public void longClick(){
//        Toast.makeText(null, "Testing longpress button", Toast.LENGTH_LONG);
//    }

    @SuppressLint("SetTextI18n")
    public View getView(final int position, View convertView, ViewGroup parent) {

        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();
        twoLineListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener deletionChoice = new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int choice){
                        switch (choice){
                            case DialogInterface.BUTTON_POSITIVE:
                                //DatabaseWriterObject.deleteService(ServiceName)
                                Intent intent = new Intent(context, BranchRequestsPage.class);
                                intent.putExtra("Account Name",branches.get(position).getEmail());
                                context.startActivity(intent);
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Not doing anything
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Would you like to select this Branch?").setPositiveButton("Yes", deletionChoice)
                        .setNegativeButton("No", deletionChoice).show();
            }
        });

//        twoLineListItem.setOnLongClickListener(new View.OnLongClickListener(){
//            public boolean onLongClick(View v){
//                Intent editIntent = new Intent(BranchListAdapter.this, ServiceCreation.class);
//                editIntent.putExtra("name", serviceToBeEdited.getName());
//                editIntent.putExtra("forms", serviceToBeEdited.getForms());
//                editIntent.putExtra("docs", serviceToBeEdited.getDocs());
//                context.startActivity(editIntent);
//                return true;
//            }
//        });

        text1.setText(branches.get(position).getEmail());
        //***getName is not working***
        text2.setText(branches.get(position).getName());

        return twoLineListItem;
    }
}