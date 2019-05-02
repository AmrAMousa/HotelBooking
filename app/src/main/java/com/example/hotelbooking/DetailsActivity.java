package com.example.hotelbooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.models.HotelModel;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ImageView hotelImage;
    TextView hotelLocation;
    TextView hotelName;
    TextView hotelPrice;
    RatingBar hotelRate;
    TextView hotelType;
    TextView hotelDescription;
    TextView hotelLocationOnMap;
    HotelModel hotelData;
    TextView noOfPeople;
    TextView noOfNight;
    TextView costPerNight;
    TextView totalCost;
    ImageButton imageButton;
    Button bookBtn;
    SharedPreferences sharedPreferencesfavorite;
    SharedPreferences sharedPreferencesBooked;
    SharedPreferences.Editor editorfavorite;
    SharedPreferences.Editor editorBooked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        hotelImage = findViewById(R.id.hotel_img_details);
        hotelLocation = findViewById(R.id.hotel_location_details);
        hotelName = findViewById(R.id.hotel_name_details);
        hotelPrice = findViewById(R.id.hotel_price_details);
        hotelRate = findViewById(R.id.hotel_rate_details);
        hotelType = findViewById(R.id.hotel_type_details);
        hotelDescription = findViewById(R.id.hotel_description_details);
        hotelLocationOnMap = findViewById(R.id.hotel_location_on_map_details);
        imageButton = findViewById(R.id.hotel_favorite_detailsBtn);
        bookBtn = findViewById(R.id.book_btn);
        sharedPreferencesfavorite = getSharedPreferences("favorite", MODE_PRIVATE);
        sharedPreferencesBooked = getSharedPreferences("Booked", MODE_PRIVATE);
        editorfavorite = sharedPreferencesfavorite.edit();
        editorBooked = sharedPreferencesBooked.edit();


        //get data__________
        Intent intent = getIntent();
        if (intent.hasExtra("itemData")) {
            hotelData = intent.getExtras().getParcelable("itemData");
            Picasso.with(this).load(hotelData.getHotelImage()).into(hotelImage);
            hotelLocation.setText(hotelData.getHotelLocation());
            hotelName.setText(hotelData.getHotelName());
            hotelPrice.setText(hotelData.getHotelPrice() + "$/night");
            hotelRate.setRating(Float.parseFloat(hotelData.getHotelRate()));
            hotelType.setText(hotelData.getHotelType() + " stars");
            hotelDescription.setText(hotelData.getHotelDescription());
            hotelLocationOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hotelData.getHotelLocationOnMap())));
                }
            });
        }


        //check if in favorites___________
        final String s = sharedPreferencesfavorite.getString(hotelData.getHotelId(), null);
        if (s == null) {
            imageButton.setImageResource(R.drawable.heart_outline);
        } else {

            imageButton.setImageResource(R.drawable.heart);
        }
        //add or remove from favorite_____________
        imageButton.setOnClickListener(new View.OnClickListener() {
            String ss = s;

            @Override
            public void onClick(View v) {

                if (ss == null) {
                    editorfavorite.putString(hotelData.getHotelId(), hotelData.getHotelId());
                    editorfavorite.commit();
                    imageButton.setImageResource(R.drawable.heart);
                    Toast.makeText(DetailsActivity.this, "added to favorite", Toast.LENGTH_LONG).show();
                    ss = sharedPreferencesfavorite.getString(hotelData.getHotelId(), null);
                } else {
                    editorfavorite.remove(hotelData.getHotelId());
                    editorfavorite.commit();
                    imageButton.setImageResource(R.drawable.heart_outline);
                    Toast.makeText(DetailsActivity.this, "removed form favorite", Toast.LENGTH_LONG).show();
                    ss = sharedPreferencesfavorite.getString(hotelData.getHotelId(), null);
                }
            }
        });


        //check if booked
        final String s2 = sharedPreferencesBooked.getString(hotelData.getHotelId(), null);
        if (s2 == null) {
            bookBtn.setText("Book Now");
            bookBtn.setBackgroundResource(R.drawable.app_background);
        } else {
            bookBtn.setText(" ");
            bookBtn.setBackgroundResource(R.drawable.completed);
        }


        //add or remove form booked
        bookBtn.setOnClickListener(new View.OnClickListener() {
            String ss2 = s2;

            @Override
            public void onClick(View v) {
                if (ss2 == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ckeck)
                            .setView(R.layout.custom_dialog)
                            .setPositiveButton("  comfirm  ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ss2 == null) {
                                        editorBooked.putString(hotelData.getHotelId(), hotelData.getHotelId());
                                        editorBooked.commit();
                                        bookBtn.setText(" ");
                                        bookBtn.setBackgroundResource(R.drawable.completed);
                                        Toast.makeText(DetailsActivity.this, "Booked", Toast.LENGTH_LONG).show();
                                        ss2 = sharedPreferencesBooked.getString(hotelData.getHotelId(), null);
                                    }
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.app_background);
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#333333"));

                    noOfPeople = alertDialog.findViewById(R.id.no_of_peaple);
                    noOfNight = alertDialog.findViewById(R.id.no_of_night);
                    costPerNight = alertDialog.findViewById(R.id.the_cost_per_night);
                    totalCost = alertDialog.findViewById(R.id.total_cost);
                    costPerNight.setText("The cost/night : " + hotelData.getHotelPrice() + "$/night");
                    totalCost.setText("Total cost :" + hotelData.getHotelPrice() + "$");

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                    builder.setTitle("cancel booking")
                            .setMessage(" Are you sure ?!")
                            .setIcon(R.drawable.warn)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editorBooked.remove(hotelData.getHotelId());
                                    editorBooked.commit();
                                    bookBtn.setText("Book Now");
                                    bookBtn.setBackgroundResource(R.drawable.app_background);
                                    Toast.makeText(DetailsActivity.this, "booked canceled", Toast.LENGTH_LONG).show();
                                    ss2 = sharedPreferencesBooked.getString(hotelData.getHotelId(), null);

                                }
                            }).setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#333333"));
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#333333"));

                }
            }
        });
    }

    public void book(View view) {


        if (view.getId() == R.id.increase_btn) {
            if (Integer.parseInt(noOfPeople.getText().toString()) >= 1 && Integer.parseInt(noOfPeople.getText().toString()) < 4) {
                int np = Integer.parseInt(noOfPeople.getText().toString()) + 1;
                int nn = Integer.parseInt(noOfNight.getText().toString());
                noOfPeople.setText("" + np);
                int roomCost = Integer.parseInt(hotelData.getHotelPrice());
                costPerNight.setText("The cost/night : " + (np * roomCost) + "$/night");
                totalCost.setText("Total cost :" + (np * nn * roomCost) + "$");
            }
        } else if (view.getId() == R.id.decrease_btn) {
            if (Integer.parseInt(noOfPeople.getText().toString()) > 1 && Integer.parseInt(noOfPeople.getText().toString()) <= 4) {
                int np = Integer.parseInt(noOfPeople.getText().toString()) - 1;
                int nn = Integer.parseInt(noOfNight.getText().toString());
                noOfPeople.setText("" + np);
                int roomCost = Integer.parseInt(hotelData.getHotelPrice());
                costPerNight.setText("The cost/night : " + (np * roomCost) + "$/night");
                totalCost.setText("Total cost :" + (np * nn * roomCost) + "$");
            }
        } else if (view.getId() == R.id.increase2_btn) {
            int np = Integer.parseInt(noOfPeople.getText().toString());
            int nn = Integer.parseInt(noOfNight.getText().toString()) + 1;
            noOfNight.setText("" + nn);
            int roomCost = Integer.parseInt(hotelData.getHotelPrice());
            costPerNight.setText("The cost/night : " + (np * roomCost) + "$/night");
            totalCost.setText("Total cost :" + (np * nn * roomCost) + "$");
        } else if (view.getId() == R.id.decrease2_btn) {
            if (Integer.parseInt(noOfNight.getText().toString()) > 1) {
                int np = Integer.parseInt(noOfPeople.getText().toString());
                int nn = Integer.parseInt(noOfNight.getText().toString()) - 1;
                noOfNight.setText("" + nn);
                int roomCost = Integer.parseInt(hotelData.getHotelPrice());
                costPerNight.setText("The cost/night : " + (np * roomCost) + "$/night");
                totalCost.setText("Total cost :" + (np * nn * roomCost) + "$");
            }
        }

    }

}
