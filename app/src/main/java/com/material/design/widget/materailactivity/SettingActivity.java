package com.material.design.widget.materailactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.anxindeli.library.utils.ToastUtil;
import com.material.design.widget.R;
import com.material.design.widget.widget.CommonItemView;

import java.lang.reflect.Method;

/**
 * Created by chawei on 2017/2/13.
 */

public class SettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);

        findView();

        initData();

        initToolbar();
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.title_back_icon);
    }


    private void initData() {
        CommonItemView civTouzi = (CommonItemView) findViewById(R.id.civ_touzi);
        civTouzi.setIconDrawable(this, R.mipmap.touzi_icon, R.color.edit_border_color, false);
        civTouzi.setTitle("我的投资");
        CommonItemView civHuikuan = (CommonItemView) findViewById(R.id.civ_huikuan);
        civHuikuan.setIconDrawable(this, R.mipmap.huikuan_icon, R.color.edit_border_color, false);
        civHuikuan.setTitle("回款计划");
        CommonItemView civJiaoyi = (CommonItemView) findViewById(R.id.civ_jiaoyi);
        civJiaoyi.setIconDrawable(this, R.mipmap.jiaoyi_icon, R.color.edit_border_color, false);
        civJiaoyi.setTitle("交易明细");
        CommonItemView civZhuanrang = (CommonItemView) findViewById(R.id.civ_zhuanrang);
        civZhuanrang.setIconDrawable(this, R.mipmap.zhuanrang_icon, R.color.edit_border_color, false);
        civZhuanrang.setTitle("我的转让");
        CommonItemView mCivToubiao = (CommonItemView) findViewById(R.id.civ_toubiao);
        mCivToubiao.setIconDrawable(this, R.drawable.toubiao_icon, R.color.edit_border_color, false);
        mCivToubiao.setTitle("自动投标");
        CommonItemView mCivZiping = (CommonItemView) findViewById(R.id.civ_ziping);
        mCivZiping.setIconDrawable(this, R.drawable.ziping_icon, R.color.edit_border_color, false);
        mCivZiping.setTitle("风险自评");

        CommonItemView civSetting = (CommonItemView) findViewById(R.id.civ_setting);
        civSetting.setIconDrawable(this, R.drawable.ic_business140, R.color.edit_border_color, true);
        civSetting.setTitle("设置");

        CommonItemView civShare = (CommonItemView) findViewById(R.id.civ_share);
        civShare.setIconDrawable(this, R.drawable.ic_back41, R.color.edit_border_color, true);
        civShare.setTitle("分享");

        civTouzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShortToast(SettingActivity.this,"别点了");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_action_search_show:
                Toast.makeText(this, "点我干嘛？", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_action_search:
                Toast.makeText(this, "你点击了搜索", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);

    }
}


//Fragment中使用
//
//        有时候需要在Fragment中使用Toolbar，比如Activity中不同的Tab显示不同的Fragment，同时每个Tab的Toolbar标题、Menu均不相同，这时在Activity中使用同一个Toolbar就相当不方便了。我们可以在每个Fragment的布局中添加各自的Toolbar，然后在Fragment中单独控制。
//
//        与Activity中使用Toolbar有所不同。替换ActionBar时，需要给setSupportActionBar方法添加作用对象：
//
//        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar)mContentView.findViewById(R.id.tb_toolbar));
//        1
//        1
//        添加Options Menu时，需要额外调用setHasOptionsMenu(true);方法，确保onCreateOptionsMenu()方法得以调用，并且onCreateOptionsMenu()方法多了一个MenuInflater参数：
//
//@Override
//public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
//        super.onCreateOptionsMenu(menu,inflater);
//        inflater.inflate(R.menu.search,menu);
//        }
