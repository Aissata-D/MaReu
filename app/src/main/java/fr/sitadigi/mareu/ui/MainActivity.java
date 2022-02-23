package fr.sitadigi.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import fr.sitadigi.mareu.R;

public class MainActivity extends AppCompatActivity {
FloatingActionButton MainBtnAdd;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainBtnAdd = findViewById(R.id.floatingActionButtonAdd);

        showFragmentListMail();
        MainBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //REPLACE FRAGMENT
                //AddMailFragment.newInstance();
               /* AddMailFragment addMailFragment = new AddMailFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_list_mail, addMailFragment ); //give your fragment container id in first parameter
                transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
                transaction.commit();
                Log.d("TAG", "onClick: fragment lancer");*/
                Intent addMailActivity = new Intent(view.getContext(), AddMailActivity.class);
                startActivity(addMailActivity);
            }
        });
    }

    void showFragmentListMail(){
        ListMailFragment listMailFragment = new ListMailFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framLayout_list_mail, listMailFragment ); //give your fragment container id in first parameter
        transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
        transaction.commit();
        Log.d("TAG", "onClick: fragment lancer");
    }
}