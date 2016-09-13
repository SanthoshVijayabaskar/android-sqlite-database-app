package sqliteappdemo.santhoshthepro.com.sqliteappdemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText edtName,edtRole,edtRating,edtID;
    Button btnSave,btnRead,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        edtName =(EditText)findViewById(R.id.edtName);
        edtRole=(EditText)findViewById(R.id.edtRole);
        edtRating=(EditText)findViewById(R.id.edtRating);
        edtID=(EditText)findViewById(R.id.edtID);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnRead=(Button)findViewById(R.id.btnRead);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnDelete=(Button)findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(edtName.getText().toString(),edtRole.getText().toString(),Integer.parseInt(edtRating.getText().toString()));

                if(isInserted == true){
                    Toast.makeText(getApplicationContext(),"Data Inserted!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Data not Inserted!",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if(res.getCount() == 0){
                    showMessage("Error","No Data Found!");
                    return;
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append("Id: "+ res.getString(0)+ "\n");
                        buffer.append("Name: "+ res.getString(1)+ "\n");
                        buffer.append("Role: "+ res.getString(2)+ "\n");
                        buffer.append("Rating: "+ res.getInt(3)+ "\n\n");
                    }
                    showMessage("Data",buffer.toString());
                }


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(edtID.getText().toString(),edtName.getText().toString(),edtRole.getText().toString(),Integer.parseInt(edtRating.getText().toString()));
                if(isUpdate == true){
                    Toast.makeText(getApplicationContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Data is not Updated",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDB.deleteData(edtID.getText().toString());
                if(deletedRows >0){
                    Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Data not deleted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
