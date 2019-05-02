package com.example.hotelbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hotelbooking.DetailsActivity;
import com.example.hotelbooking.R;
import com.example.hotelbooking.models.HotelModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HotelItemAdapter extends RecyclerView.Adapter<HotelItemAdapter.ViewHolder> implements Filterable  {

    Context context;
    List<HotelModel> hotelModels = new ArrayList<>();
    List<HotelModel> hotelModelsFull;



    public HotelItemAdapter(Context context) {
        this.context = context;
    }

     public void setData(List<HotelModel> hotelModels) {
        this.hotelModels = hotelModels;
        hotelModelsFull=new ArrayList<>(hotelModels);

    }

    @NonNull
    @Override
    public HotelItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.hotelName.setText(hotelModels.get(i).getHotelName());
        viewHolder.hotelLocation.setText(hotelModels.get(i).getHotelLocation());
        viewHolder.hotelType.setText(hotelModels.get(i).getHotelType()+" stars");
        viewHolder.hotelPrice.setText(hotelModels.get(i).getHotelPrice()+"$/night");
        viewHolder.hotelRate.setRating(Float.parseFloat(hotelModels.get(i).getHotelRate()));
        Picasso.with(context).load(hotelModels.get(i).getHotelImage()).placeholder(R.color.colorPrimaryDark).into(viewHolder.hotelImage);

    }

    @Override
    public int getItemCount() {
        if (hotelModels == null) {
            return 0;
        }
        return hotelModels.size();
    }

    @Override
    public Filter getFilter() {
        return hotelModelsFilter;
    }
    private Filter hotelModelsFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HotelModel>filteredList = new ArrayList<>();
            if (constraint ==null || constraint.length()==0){
                filteredList.addAll(hotelModelsFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (HotelModel hotelModel:hotelModelsFull) {
                        if (hotelModel.getHotelName().toLowerCase().trim().contains(filterPattern)){
                            filteredList.add(hotelModel);
                        }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hotelModels.clear();
            hotelModels.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView hotelImage;
        TextView hotelLocation;
        TextView hotelName;
        TextView hotelPrice;
        RatingBar hotelRate;
        TextView hotelType;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotel_img);
            hotelLocation = itemView.findViewById(R.id.hotel_location);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelPrice = itemView.findViewById(R.id.hotel_price);
            hotelRate = itemView.findViewById(R.id.hotel_rate);
            hotelType = itemView.findViewById(R.id.hotel_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("itemData",hotelModels.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
