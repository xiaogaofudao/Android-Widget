package com.gaogeek.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.gaogeek.toast.Toast;
import com.gaogeek.dialog.HintDialog;

public class MainActivity extends AppCompatActivity {

//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"测试测试 Toast", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.Dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HintDialog.makeText(MainActivity.this, "测试信息").show();
//                HintDialog mDialog = new HintDialog(MainActivity.this);
//                mDialog.show();
            }
        });
    }
}
