package com.bcit.myapplication;

import androidx.fragment.app.FragmentContainerView;

import java.io.Serializable;

/**
 * Creates a model for fragment container view.
 */
public class FragmentContainerViewModel implements Serializable {
    private FragmentContainerView fragmentContainerView;

    public FragmentContainerViewModel(FragmentContainerView fragmentContainerView) {
        this.fragmentContainerView = fragmentContainerView;
    }

    public FragmentContainerView getFragmentContainerView() {
        return fragmentContainerView;
    }
}
