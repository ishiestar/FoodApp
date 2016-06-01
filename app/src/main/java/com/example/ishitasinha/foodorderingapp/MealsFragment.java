package com.example.ishitasinha.foodorderingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ishitasinha.foodorderingapp.data.Items;

import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMealCategoryClickListener} interface
 * to handle interaction events.
 * Use the {@link MealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BF_COUNT = "bfCount";
    public static final String L_COUNT = "lCount";
    public static final String T_COUNT = "tCount";
    public static final String DN_COUNT = "dnCount";

    // TODO: Rename and change types of parameters
    private int bfCount = 0;
    private int lCount = 0;
    private int tCount = 0;
    private int dnCount = 0;

    Realm realm;
    private OnMealCategoryClickListener mListener;

    TextView breakfastCount, lunchCount, teaCount, dinnerCount;
    LinearLayout llBreakfast, llLunch, llTea, llDinner;

    public MealsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment MealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealsFragment newInstance(int bfCount, int lCount, int tCount, int dnCount) {
        MealsFragment fragment = new MealsFragment();
        Bundle args = new Bundle();
        args.putInt(BF_COUNT, bfCount);
        args.putInt(L_COUNT, lCount);
        args.putInt(T_COUNT, tCount);
        args.putInt(DN_COUNT, dnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = FoodApp.getInstance(getContext()).realm;
        if (getArguments() != null) {
            bfCount = getArguments().getInt(BF_COUNT);
            lCount = getArguments().getInt(L_COUNT);
            tCount = getArguments().getInt(T_COUNT);
            dnCount = getArguments().getInt(DN_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Items> bfItems = realm.where(Items.class).equalTo("type", FoodApp.TYPE_BREAKFAST).findAll();
        bfCount = 0;
        for (Items bfItem : bfItems) {
            bfCount += bfItem.getQuantity();
        }

        List<Items> lItems = realm.where(Items.class).equalTo("type", FoodApp.TYPE_LUNCH).findAll();
        lCount = 0;
        for (Items lItem : lItems) {
            lCount += lItem.getQuantity();
        }

        List<Items> tItems = realm.where(Items.class).equalTo("type", FoodApp.TYPE_TEA).findAll();
        tCount = 0;
        for (Items tItem : tItems) {
            tCount += tItem.getQuantity();
        }

        List<Items> dnItems = realm.where(Items.class).equalTo("type", FoodApp.TYPE_DINNER).findAll();
        dnCount = 0;
        for (Items dnItem : dnItems) {
            dnCount += dnItem.getQuantity();
        }
        breakfastCount.setText(String.valueOf(bfCount));
        lunchCount.setText(String.valueOf(lCount));
        teaCount.setText(String.valueOf(tCount));
        dinnerCount.setText(String.valueOf(dnCount));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_meals, container, false);
        breakfastCount = (TextView) rootView.findViewById(R.id.breakfast_count);
        breakfastCount.setText(String.valueOf(bfCount));
        lunchCount = (TextView) rootView.findViewById(R.id.lunch_count);
        lunchCount.setText(String.valueOf(lCount));
        teaCount = (TextView) rootView.findViewById(R.id.tea_count);
        teaCount.setText(String.valueOf(tCount));
        dinnerCount = (TextView) rootView.findViewById(R.id.dinner_count);
        dinnerCount.setText(String.valueOf(dnCount));

        llBreakfast = (LinearLayout) rootView.findViewById(R.id.ll_breakfast);
        llBreakfast.setOnClickListener(this);
        llLunch = (LinearLayout) rootView.findViewById(R.id.ll_lunch);
        llLunch.setOnClickListener(this);
        llTea = (LinearLayout) rootView.findViewById(R.id.ll_tea);
        llTea.setOnClickListener(this);
        llDinner = (LinearLayout) rootView.findViewById(R.id.ll_dinner);
        llDinner.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMealCategoryClickListener) {
            mListener = (OnMealCategoryClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMealCategoryClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.ll_breakfast:
                    mListener.onMealCategoryClick(FoodApp.TYPE_BREAKFAST);
                    break;
                case R.id.ll_lunch:
                    mListener.onMealCategoryClick(FoodApp.TYPE_LUNCH);
                    break;
                case R.id.ll_tea:
                    mListener.onMealCategoryClick(FoodApp.TYPE_TEA);
                    break;
                case R.id.ll_dinner:
                    mListener.onMealCategoryClick(FoodApp.TYPE_DINNER);
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMealCategoryClickListener {
        // TODO: Update argument type and name
        void onMealCategoryClick(String mealType);
    }
}
