package com.example.sigma72;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sigma72.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public void Change (View view) {
        Fragment fragment= null;
        switch (view.getId()){
            case R.id.button:
                fragment = new BlankFragment();
                break;
            case R.id.button2:
                fragment = new BlankFragment2();
                break;
            case R.id.button3:
                fragment = new BlankFragment3();
                break;
            case R.id.button4:
                fragment = new BlankFragment4();
                break;
            case R.id.button5:
                fragment = new BlankFragment5();
                break;
            case R.id.button6:
                fragment = new BlankFragment6();
                break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fr,fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}