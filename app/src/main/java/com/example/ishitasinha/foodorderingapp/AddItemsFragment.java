package com.example.ishitasinha.foodorderingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ishitasinha.foodorderingapp.data.Items;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnAddItemsInteractionListener}
 * interface.
 */
public class AddItemsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String TYPE = "type";
    // TODO: Customize parameters
    private String type;
    RecyclerView recyclerView;
    private OnAddItemsInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddItemsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AddItemsFragment newInstance(String type) {
        AddItemsFragment fragment = new AddItemsFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Items> itemsList = FoodApp.getCatalog(type, getContext());
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(itemsList, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddItemsInteractionListener) {
            mListener = (OnAddItemsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddItemsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddItemsInteractionListener {
        // TODO: Update argument type and name
        void onAddItemsInteraction(Items item);
    }
}
