package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import novigrad.R;
import novigrad.database.DatabaseWriter;

public class BranchAddressSet extends AppCompatActivity {
    EditText addr;
    Button addrBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_address_set);
        addr =  findViewById(R.id.ADDRIN);
        addrBtn = findViewById(R.id.addrbtn);
        addrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseWriter dbWriter = new DatabaseWriter();
                dbWriter.addAddress(getIntent().getStringExtra("Account Name"),addr.getText().toString());
                Toast.makeText(getApplicationContext(),"Address updated",Toast.LENGTH_SHORT).show();
            }
        });
    }
}