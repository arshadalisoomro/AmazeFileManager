/*
 * Copyright (C) 2014 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>
 *
 * This file is part of Amaze File Manager.
 *
 * Amaze File Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.amaze.filemanager.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.amaze.filemanager.R;
import com.amaze.filemanager.utils.PreferenceUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Arrays;
import java.util.Calendar;

public class Preferences extends AppCompatActivity {
    int theme, skinStatusBar;
    String skin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences Sp = PreferenceManager.getDefaultSharedPreferences(this);

        int th = Integer.parseInt(Sp.getString("theme", "0"));
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        theme = th;
        if (th == 2) {
            if(hour<=6 || hour>=18) {
                theme = 1;
            } else
                theme = 0;
        }

        if (theme == 1) {
            setTheme(R.style.appCompatDark);
        } else {
            setTheme(R.style.appCompatLight);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefsfrag);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        skin = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("skin_color", "#3f51b5");

        String x = PreferenceUtils.getStatusColor(skin);
        skinStatusBar = Color.parseColor(x);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(skin)));
        int sdk=Build.VERSION.SDK_INT;

        /*if(sdk==20 || sdk==19) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(Color.parseColor(skin));

            FrameLayout.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) findViewById(R.id.preferences).getLayoutParams();
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
            p.setMargins(0, config.getStatusBarHeight(), 0, 0);
        }else*/ if(Build.VERSION.SDK_INT>=21){
            boolean colourednavigation=Sp.getBoolean("colorednavigation",true);
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(PreferenceUtils.getStatusColor(skin)));
            if(colourednavigation)
                window.setNavigationBarColor(Color.parseColor(PreferenceUtils.getStatusColor(skin)));

        }
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Preferences.this, MainActivity.class);
        in.setAction(Intent.ACTION_MAIN);
        final int enter_anim = android.R.anim.fade_in;
        final int exit_anim = android.R.anim.fade_out;
        Activity activity=this;
        activity.overridePendingTransition(enter_anim, exit_anim);
        activity.finish();
        activity.overridePendingTransition(enter_anim, exit_anim);
        activity.startActivity(in);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                Intent in = new Intent(Preferences.this, MainActivity.class);
                in.setAction(Intent.ACTION_MAIN);
                final int enter_anim = android.R.anim.fade_in;
                final int exit_anim = android.R.anim.fade_out;
                Activity activity=this;
                activity.overridePendingTransition(enter_anim, exit_anim);
                activity.finish();
                activity.overridePendingTransition(enter_anim, exit_anim);
                activity.startActivity(in);
                return true;

        }
        return true;
    }
}