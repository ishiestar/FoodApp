package com.example.ishitasinha.foodorderingapp;

import android.app.Application;
import android.content.Context;

import com.example.ishitasinha.foodorderingapp.data.Items;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ishitasinha on 30/05/16.
 */
public class FoodApp extends Application {
    RealmConfiguration configuration;
    Realm realm;
    static FoodApp instance;
    private static List<Items> cart;

    public static final String TYPE_BREAKFAST = "Breakfast";
    public static final String TYPE_LUNCH = "Lunch";
    public static final String TYPE_TEA = "Tea";
    public static final String TYPE_DINNER = "Dinner";

    public FoodApp(Context context) {
        if (realm == null) {
            configuration = new RealmConfiguration.Builder(context).build();
            realm = Realm.getInstance(configuration);
        }
        if (realm != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(new Items("BF1", "Idly", "veg", TYPE_BREAKFAST, "http://adukkala.com/files/2013/07/Idly-and-Coconut-Chutney-Adukkala.jpg"));
            realm.copyToRealmOrUpdate(new Items("BF2", "Upma", "veg", TYPE_BREAKFAST, "http://www.eknazar.com/Topics/Recipes/uploaded/RI2208_1iupma.jpg"));
            realm.copyToRealmOrUpdate(new Items("BF3", "Poha", "veg", TYPE_BREAKFAST, "http://www.roaringraw.com/wp-content/uploads/2013/03/poha.jpg"));
            realm.copyToRealmOrUpdate(new Items("BF4", "Dosa", "veg", TYPE_BREAKFAST, "http://dosamatic.com/wp-content/uploads/2014/08/paper-dosa.jpg"));
            realm.copyToRealmOrUpdate(new Items("LU1", "Rice and vegetables", "veg", TYPE_LUNCH, "http://media.jrn.com/images/425*500/b99628129z.1_20151203185639_000_g2ldgr45.1-1.jpg"));
            realm.copyToRealmOrUpdate(new Items("LU2", "Roti and vegetables", "veg", TYPE_LUNCH, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("LU3", "Rice and chicken", "non-veg", TYPE_LUNCH, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("LU4", "Roti and chicken", "non-veg", TYPE_LUNCH, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("TE1", "Cake", "non-veg", TYPE_TEA, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("TE2", "Eggless Cake", "veg", TYPE_TEA, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("TE3", "Samosa", "veg", TYPE_TEA, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("TE4", "Coffee", "veg", TYPE_TEA, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("DN1", "Biryani", "veg", TYPE_DINNER, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("DN2", "Chicken Biryani", "non-veg", TYPE_DINNER, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("DN3", "Rice and vegetables", "veg", TYPE_DINNER, "http://slo-myindia.com/assets/no_photo.png"));
            realm.copyToRealmOrUpdate(new Items("DN4", "Roti and vegetables", "veg", TYPE_DINNER, "http://slo-myindia.com/assets/no_photo.png"));
            realm.commitTransaction();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static FoodApp getInstance(Context context) {
        if (instance == null) {
            instance = new FoodApp(context);
        }
        return instance;
    }

    public static List<Items> getCatalog(String type, Context context) {
        return FoodApp.getInstance(context).realm.where(Items.class).equalTo("type", type).findAll();
    }

    public static List<Items> getCart(Context context) {
        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.addAll(FoodApp.getInstance(context).realm.where(Items.class).greaterThan("quantity", 0).findAll());
        return cart;
    }
}
