package com.gaogeek.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gaogeek.dialog.OnConfirmListener;
import com.gaogeek.dialog.HintDialog;
import com.gaogeek.toast.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"测试测试 Toast", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.Toast2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"测试测试 Toast2222222222222", Toast.LENGTH_SHORT).show();
                Toast mToast = Toast.makeText(MainActivity.this,"测试测试 Toast 2222222", Toast.LENGTH_SHORT);
//                mToast.setText(R.string.app_name);
                mToast.setDuration(5000);
                mToast.show();
            }
        });

        findViewById(R.id.Dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HintDialog.makeText(MainActivity.this, "第三方是试信息大师傅大师傅士大夫士大夫稍等发测试信息").show();
            }
        });

        findViewById(R.id.Dialog2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HintDialog.makeText(MainActivity.this, "测试消息2，点击确定弹出toast").setOnConfirmListener(new OnConfirmListener() {
                    @Override
                    public void OnConfirm() {
                        Toast.makeText(MainActivity.this,"确定", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
}
