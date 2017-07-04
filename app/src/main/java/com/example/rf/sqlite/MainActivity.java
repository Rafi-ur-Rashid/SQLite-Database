package com.example.rf.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    dbClass dbclass;
    EditText nameField,passField,newPassField;
    TextView passShow;
    Button Insert;
    String name,pass;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameField=(EditText)findViewById(R.id.namw);
        passField=(EditText)findViewById(R.id.showPass);
        newPassField=(EditText)findViewById(R.id.newPass);
        Insert=(Button)findViewById(R.id.insert);
        dbclass=new dbClass(this);
        sqLiteDatabase=dbclass.getWritableDatabase();
        passShow=(TextView)findViewById(R.id.showPass);
    }
    public void InsertData(View view){
        name=nameField.getText().toString();
        pass=passField.getText().toString();
        dbclass.insertUser(name,pass,sqLiteDatabase);
    }
    public void showData(View view){
        Toast.makeText(MainActivity.this,dbclass.selectAllData(sqLiteDatabase),Toast.LENGTH_LONG).show();
    }
    public void flashData(View view) {
//        Intent intent = new Intent(MainActivity.this, displayData.class);
//        intent.putExtra("dataClass", dbclass);
//        startActivity(intent);
          String name=nameField.getText().toString();
          int id=Integer.parseInt(name);
          Toast.makeText(MainActivity.this,dbclass.selectData(sqLiteDatabase,id),Toast.LENGTH_LONG).show();

    }
    public void updateData(View view){
        String name=nameField.getText().toString();
        int id=Integer.parseInt(name);
        String pass=passField.getText().toString();
        String newpass=newPassField.getText().toString();
        String tempPass=dbclass.selectData(sqLiteDatabase,id);
        if(tempPass.equals(pass)){
            dbclass.updateData(sqLiteDatabase,id,newpass);
            Toast.makeText(MainActivity.this,dbclass.selectAllData(sqLiteDatabase),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(MainActivity.this,"UserName or Password doesn't match",Toast.LENGTH_LONG).show();
    }
    public void deleteData(View view){
        String name=nameField.getText().toString();
        int id=Integer.parseInt(name);
        String pass=passField.getText().toString();
        String tempPass=dbclass.selectData(sqLiteDatabase,id);
        if(tempPass.equals(pass)){
            dbclass.deleteData(sqLiteDatabase,name);
            Toast.makeText(MainActivity.this,dbclass.selectAllData(sqLiteDatabase),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(MainActivity.this,"UserName or Password doesn't match",Toast.LENGTH_LONG).show();

    }

}
