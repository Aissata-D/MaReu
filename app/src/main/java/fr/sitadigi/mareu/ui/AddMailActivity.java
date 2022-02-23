package fr.sitadigi.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import fr.sitadigi.mareu.R;

public class AddMailActivity extends AppCompatActivity implements AddMailFragment.onButtonAddReunionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mail);

        AddMailFragment addMailFragment = new AddMailFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.framLayout_add_mail, addMailFragment ); //give your fragment container id in first parameter
        transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
        transaction.commit();
    }

    @Override
    public void OnButtonAddReunionClick(View view) {
        Log.d("TAG", "OnButtonAddReunionClick: Callback reussi!!!");
    finish();
    }
}