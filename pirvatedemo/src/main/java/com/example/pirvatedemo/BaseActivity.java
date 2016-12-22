package com.example.pirvatedemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;




/**
 * Created by liubiao on 2016/12/2.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private boolean isShowNavigatView;
    public abstract int  getLayoutId() ;
    public   String i="Base";


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initAnnotation();
        getLayoutId();
        setContentView(getLayoutId());
        if(isShowNavigatView)
        {

            ((TextView)findViewById(R.id.tv)).setText(i);
        }

    }
    private void initAnnotation() {
        isShowNavigatView= getClass().getAnnotation(NavigatInject.class).value();
    }



}
