package com.bcit.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.EventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeBotNavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeBotNavFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private MenuItem group;

    // TODO: Rename and change types of parameters
    private FragmentContainerViewModel mParam1;

    public EmployeeBotNavFragment() {
        // Required empty public constructor
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            FragmentActivity fragmentActivity = getActivity();
//            fragmentActivity
            switch (item.getItemId()) {
                case R.id.groupFragment:
                    GroupFragment groupFragment = new GroupFragment();
                    FragmentContainerView containerView = mParam1.getFragmentContainerView();
                    FragmentManager groupFragmentManager = getChildFragmentManager();
                    groupFragmentManager.beginTransaction().replace(containerView.getId(), groupFragment).commit();
                    return true;
                case R.id.groupPostFragment:
                    GroupPostFragment groupPostFragment = new GroupPostFragment();
                    FragmentManager groupPostFragmentManager = getChildFragmentManager();
                    //groupPostFragmentManager.beginTransaction().replace(R.id.fragmentContainerView_bot_nav_main_menu, groupPostFragment).commit();
                    return true;
                case R.id.commentPostFragment:
                    CommentPostFragment commentPostFragment = new CommentPostFragment();
                    FragmentManager commentFragmentManager = getChildFragmentManager();
                    //commentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView_bot_nav_main_menu, commentPostFragment).commit();
                    return true;
            }
            return false;
        }
    };
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EmployeeBotNavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeBotNavFragment newInstance(FragmentContainerViewModel param1) {
        EmployeeBotNavFragment fragment = new EmployeeBotNavFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (FragmentContainerViewModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.groupFragment) {
            GroupFragment groupFragment = new GroupFragment();
            FragmentContainerView containerView = mParam1.getFragmentContainerView();
            FragmentManager groupFragmentManager = getChildFragmentManager();
            groupFragmentManager.beginTransaction().replace(containerView.getId(), groupFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_employee_bot_nav, container, false);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_nav_view_employee);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MenuItem menuItem = view.findViewById(R.id.groupFragment);
        this.group = menuItem;
    }

    public MenuItem getGroup() {
        return group;
    }
}