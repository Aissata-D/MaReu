package fr.sitadigi.mareu.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import fr.sitadigi.mareu.R;

//import androidx.fragment.app.FragmentActivity;
//import android.app.FragmentTransaction;

public class AddMailActivity extends AppCompatActivity implements AddMailFragment.onButtonAddReunionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mail);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AddMailFragment addMailFragment = (AddMailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.framLayout_add_mail);
        if (addMailFragment == null) {
            addMailFragment = new AddMailFragment();
            transaction.add(R.id.framLayout_add_mail, addMailFragment); //give your fragment container id in first parameter
        } else transaction.show(addMailFragment);
        // transaction.addToBackStack(null);  //if written, this transaction will be added to backstack
        transaction.commit();
    }

    @Override
    public void OnButtonAddReunionClick(View view) {
        Log.d("TAG", "OnButtonAddReunionClick: Callback reussi!!!");
        finish();
    }
}