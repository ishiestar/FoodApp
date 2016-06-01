package com.example.ishitasinha.foodorderingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishitasinha.foodorderingapp.data.Items;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private final List<Items> mValues;
    private final AddItemsFragment.OnAddItemsInteractionListener mListener;
    Context mContext;
    Realm realm;

    public CartRecyclerViewAdapter(List<Items> items, AddItemsFragment.OnAddItemsInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        realm = FoodApp.getInstance(mContext).realm;
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mValues.get(position).getName());
        holder.quantity.setText(String.valueOf(mValues.get(position).getQuantity()));
        holder.description.setText(mValues.get(position).getDescription());
        holder.type.setText(mValues.get(position).getType());
        holder.type.setVisibility(View.VISIBLE);
        Picasso.with(mContext).load(mValues.get(position).getImgUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name, type, description, quantity;
        Button increaseBtn, decreaseBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_image);
            name = (TextView) itemView.findViewById(R.id.item_name);
            type = (TextView) itemView.findViewById(R.id.item_type);
            quantity = (TextView) itemView.findViewById(R.id.item_quantity);
            description = (TextView) itemView.findViewById(R.id.item_description);
            decreaseBtn = (Button) itemView.findViewById(R.id.decrease_button);
            increaseBtn = (Button) itemView.findViewById(R.id.increase_button);
            itemView.setOnClickListener(this);
            increaseBtn.setOnClickListener(this);
            decreaseBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.decrease_button:
                case R.id.increase_button:
                    changeQty(view);
                    break;
                default:
                    mListener.onAddItemsInteraction(mValues.get(getAdapterPosition()));
            }
        }

        private void changeQty(View view) {
            int qty = Integer.parseInt(quantity.getText().toString());
            switch (view.getId()){
                case R.id.increase_button:
                    qty++;
                    break;
                case R.id.decrease_button:
                    if (qty == 0) {
                        Toast.makeText(mContext, "Quantity cannot be less than zero!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    qty--;
                    break;
                default: break;
            }
            quantity.setText(String.valueOf(qty));
            realm.beginTransaction();
            mValues.get(getAdapterPosition()).setQuantity(qty);
            realm.commitTransaction();
        }
    }
}
