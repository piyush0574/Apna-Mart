package com.example.testapp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductSpecificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductSpecificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView productSpecificationRecycleView;

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductSpecificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductSpecificationFragment newInstance(String param1, String param2) {
        ProductSpecificationFragment fragment = new ProductSpecificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Step 6
        // Inflate the layout for this fragment ,set the linear layout to recycle view and pass it the adaptor with some
        //dummy list
        View view= inflater.inflate(R.layout.fragment_product_specification, container, false);
        productSpecificationRecycleView=view.findViewById(R.id.product_specification_recycle_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
       linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecycleView.setLayoutManager(linearLayoutManager);

        // Dummy List
        List<ProductSpecificationModal> productSpecificationModalList=new ArrayList<>();
        productSpecificationModalList.add(new ProductSpecificationModal(0,"General"));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM","4GB",1));

        productSpecificationModalList.add(new ProductSpecificationModal("ROM","14GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM2","4GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM3","4GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM4","4GB",1));

        productSpecificationModalList.add(new ProductSpecificationModal("RAM","4GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal(0,"Special     v"));

        productSpecificationModalList.add(new ProductSpecificationModal("ROM","14GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM2","4GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM3","4GB",1));
        productSpecificationModalList.add(new ProductSpecificationModal("RAM4","4GB",1));
        ;


        ProductSpecificationAdaptor productSpecificationAdaptor=new ProductSpecificationAdaptor(productSpecificationModalList);
        productSpecificationRecycleView.setAdapter(productSpecificationAdaptor);
        productSpecificationAdaptor.notifyDataSetChanged();

        return view;
    }
}