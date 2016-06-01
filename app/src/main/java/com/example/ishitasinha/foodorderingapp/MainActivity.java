package com.example.ishitasinha.foodorderingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ishitasinha.foodorderingapp.data.Items;

public class MainActivity extends AppCompatActivity implements MealsFragment.OnMealCategoryClickListener,
        AddItemsFragment.OnAddItemsInteractionListener, FragmentManager.OnBackStackChangedListener {

    String type;
    FragmentManager fragmentManager;
    MealsFragment mealsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mealsFragment = MealsFragment.newInstance(0, 0, 0, 0);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        fragmentManager
                .beginTransaction()
                .add(R.id.container, mealsFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, new CartFragment())
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return (!(fragmentManager.findFragmentById(R.id.container) instanceof CartFragment));
    }

    @Override
    public void onMealCategoryClick(String mealType) {
        type = mealType;
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, AddItemsFragment.newInstance(type))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddItemsInteraction(Items item) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(item.getId()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        invalidateOptionsMenu();
    }
}
