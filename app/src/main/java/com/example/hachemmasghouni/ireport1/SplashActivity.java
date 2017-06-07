package com.example.hachemmasghouni.ireport1;

import android.content.Intent;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;



public class SplashActivity extends AwesomeSplash {

//No need for the layout thats why uncommented
  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }*/

    @Override
    public void initSplash(ConfigSplash configSplash) {

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Background animation and colours
        configSplash.setBackgroundColor(R.color.colorPrimaryLight);
        configSplash.setAnimCircularRevealDuration(3000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);


        //Logo and duration
        configSplash.setLogoSplash(R.drawable.ic_eye);
        configSplash.setAnimLogoSplashDuration(3000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Landing);


        //Title on the screen
        configSplash.setTitleSplash(getString(R.string.text_report));
        configSplash.setTitleTextColor(R.color.icons);
        configSplash.setTitleTextSize(24f);
        configSplash.setAnimPathFillingDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.BounceIn);
    }


    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
    }
}

