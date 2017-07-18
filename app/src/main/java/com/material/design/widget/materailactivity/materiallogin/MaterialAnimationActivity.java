package com.material.design.widget.materailactivity.materiallogin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.material.design.widget.R;
import com.material.design.widget.widget.NewTextInputLayout;

/**
 * Created by chawei on 2017/2/12.
 */

public class MaterialAnimationActivity extends AppCompatActivity {

    private FloatingActionButton mFabPlus;
    private Button mBtnLogin;
    private NewTextInputLayout mTextInputPassword;
    private EditText mEtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_material_animation_layout);

        findView();
        initListener();
    }

    private void findView(){
        mFabPlus = (FloatingActionButton) findViewById(R.id.id_fab_plus);
        mTextInputPassword = (NewTextInputLayout) findViewById(R.id.id_textinput_password);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
    }

    private void initListener(){
        mFabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(MaterialAnimationActivity.this, mFabPlus, mFabPlus.getTransitionName());
                    startActivity(new Intent(MaterialAnimationActivity.this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(MaterialAnimationActivity.this, RegisterActivity.class));
                }
            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                int length=sequence.toString().length();
                if (length > 6) {
                    mTextInputPassword.setErrorEnabled(true);
                    mTextInputPassword.setError("密码少于6位");
                }else{
                    mTextInputPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
