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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

import novigrad.R;
import novigrad.core.BranchAccount;

public class BranchListRateAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BranchAccount> branches;

    public BranchListRateAdapter(Context context, ArrayList<BranchAccount> branches) {
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
        final DatabaseWriter dbWriter = new DatabaseWriter();

        twoLineListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener deletionChoice = new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int choice){
                        switch (choice){
                            case DialogInterface.BUTTON_POSITIVE:
                                final AlertDialog.Builder popDialog = new AlertDialog.Builder(context);

                                LinearLayout linearLayout = new LinearLayout(context);
                                final RatingBar rating = new RatingBar(context);

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                rating.setLayoutParams(lp);
                                rating.setNumStars(5);
                                rating.setStepSize((float) 0.5);

                                linearLayout.addView(rating);

                                popDialog.setIcon(android.R.drawable.btn_star_big_on);
                                popDialog.setTitle("Rate: ");

                                popDialog.setView(linearLayout);

                                rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rate, boolean b) {
                                        dbWriter.addRating(branches.get(position).getEmail(), rate);
                                    }
                                });

                                // Button OK
                                popDialog.setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int choice) {

                                                Toast.makeText(context,"Rating Successful for " + branches.get(position).getEmail(),Toast.LENGTH_SHORT).show();
                                            }

                                        })

                                 // Button Cancel
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int choice) {
                                                        dialog.cancel();
                                                    }
                                                });

                                popDialog.create();
                                popDialog.show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);


                builder.setMessage("Please Rate selected Branch").setPositiveButton("Okay", deletionChoice)
                        .setNegativeButton("No", deletionChoice).show();
            }
        });
        text1.setText(branches.get(position).getEmail());
        return twoLineListItem;
    }
}
