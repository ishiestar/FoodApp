package com.example.ishitasinha.foodorderingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishitasinha.foodorderingapp.data.Items;
import com.squareup.picasso.Picasso;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link /*OnDetailsFragmentInteractionListener*//*} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ITEM_ID = "param1";

    private String itemId;
    Realm realm;
    ImageView image;
    TextView name, details, quantity;
    Button increaseBtn, decreaseBtn;
    Items item;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String itemId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        image = (ImageView) rootView.findViewById(R.id.detail_image);
        decreaseBtn = (Button) rootView.findViewById(R.id.detail_decrease_button);
        increaseBtn = (Button) rootView.findViewById(R.id.detail_increase_button);
        name = (TextView) rootView.findViewById(R.id.detail_name);
        details = (TextView) rootView.findViewById(R.id.detail_description);
        quantity = (TextView) rootView.findViewById(R.id.detail_quantity);

        realm = FoodApp.getInstance(getContext()).realm;
        item = realm.where(Items.class).equalTo("id", itemId).findFirst();
        Picasso.with(getContext()).load(item.getImgUrl()).into(image);
        name.setText(item.getName());
        details.setText(item.getDescription());
        quantity.setText(String.valueOf(item.getQuantity()));
        decreaseBtn.setOnClickListener(this);
        increaseBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int qty = Integer.parseInt(quantity.getText().toString());
        switch (view.getId()) {
            case R.id.detail_increase_button:
                qty++;
                break;
            case R.id.detail_decrease_button:
                if (qty == 0) {
                    Toast.makeText(getContext(), "Quantity cannot be less than zero!", Toast.LENGTH_SHORT).show();
                    return;
                }
                qty--;
                break;
            default:
                break;
        }
        quantity.setText(String.valueOf(qty));
        realm.beginTransaction();
        item.setQuantity(qty);
        realm.commitTransaction();
    }
}
