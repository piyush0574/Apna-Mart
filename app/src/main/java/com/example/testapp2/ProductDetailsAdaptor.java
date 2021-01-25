package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

public class ProductDetailsAdaptor extends FragmentPagerAdapter {
    private int TotalTabCount;
    public ProductDetailsAdaptor(@NonNull FragmentManager fm,int TotalTabCount) {
        super(fm);
        this.TotalTabCount=TotalTabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ProductDespcriptionFragment productDespcriptionFragment1=new ProductDespcriptionFragment();
                return productDespcriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment2=new ProductSpecificationFragment();
                return productSpecificationFragment2 ;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TotalTabCount;
    }
}
