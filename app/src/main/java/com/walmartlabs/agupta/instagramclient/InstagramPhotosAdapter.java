package com.walmartlabs.agupta.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by agupta on 10/12/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

    }
    //use the template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView userName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvCaption.setText(photo.caption);
        // clear out the image view as we might be using a recycled item
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageURL).into(ivPhoto);
        userName.setText(photo.username);

        return convertView;
    }
}
