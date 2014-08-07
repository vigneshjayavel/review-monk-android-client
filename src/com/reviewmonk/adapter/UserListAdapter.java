package com.reviewmonk.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reviewmonk.R;

public class UserListAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;

  public UserListAdapter(Context context, String[] values) {
    super(context, R.layout.users_list_row, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.users_list_row, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.user_id);
   
//    ImageView imageView = (ImageView) rowView.findViewById(R.id.gadget_brand);
    textView.setText(values[position]);
  
    // Change the icon for Windows and iPhone
    String s = values[position];

    return rowView;
  }
} 