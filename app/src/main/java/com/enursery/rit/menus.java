package com.enursery.rit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        //Button btn = (Button) findViewById(R.id.nurseryowner);
        Button btn1=(Button) findViewById(R.id.supplier);
        Button btn2=(Button) findViewById(R.id.customer);

       /* //btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchactivity();

            }
        });*/
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchactivity1();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchactivity2();
            }
        });
    }
    /*private void launchactivity()
    {
        Intent intent=new Intent(this,login.class);
        startActivity(intent);

    }*/
    private void launchactivity1()
    {
        Intent intent=new Intent(this,supplier_login.class);
        startActivity(intent);

    }
    private void launchactivity2()
    {
        Intent intent=new Intent(this,customer_login.class);
        startActivity(intent);

    }

}



