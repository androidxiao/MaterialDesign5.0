package com.material.design.widget.materailactivity.dbdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.material.design.widget.R;
import com.material.design.widget.materailactivity.dbdemo.db.DBManager;
import com.material.design.widget.materailactivity.dbdemo.model.Student;

/**
 * Created by chawei on 2017/2/21.
 */

public class DbDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_db_demo_layout);

        TextView tv= (TextView) findViewById(R.id.id_tv_db_content);

        Student student = new Student();
        student.setUid("1");
        student.setName("小明");
        student.setSex("男");
        student.setAge("10");

        DBManager.getDBManager().insertData(student);

//        DBManager.getDBManager().deleteData();
        Student student1 = DBManager.getDBManager().queryData("1");
        tv.setText(student1.getName());

    }
}
