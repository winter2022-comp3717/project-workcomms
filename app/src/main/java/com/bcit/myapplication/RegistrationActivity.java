package com.bcit.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (msg.equals("Employer")) {
            Employer employer = new Employer("Xiang", "Zhu", "xzhu1113@gmail.com", 6615095);
            EmployerRegistrationFragment employerRegistrationFragment = EmployerRegistrationFragment.newInstance(employer);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView_registration, employerRegistrationFragment);
            fragmentTransaction.commit();
        }
        if (msg.equals("Employee")) {
            Employee employee = new Employee("Ross", "Roswell", "ross@gmail.com", 77777);
            EmployeeRegistrationFragment employeeRegistrationFragment = EmployeeRegistrationFragment.newInstance(employee);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView_registration, employeeRegistrationFragment);
            fragmentTransaction.commit();
        }
    }
}