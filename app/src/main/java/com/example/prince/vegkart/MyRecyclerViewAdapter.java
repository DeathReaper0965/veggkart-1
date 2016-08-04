package com.example.prince.vegkart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Creator: vbarad
 * Date: 2016-07-29
 * Project: veggkart
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
  private Context context;
  private ArrayList<Product> products;

  public MyRecyclerViewAdapter(Context context, ArrayList<Product> products) {
    this.context = context;
    this.products = products;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, parent, false);
    ViewHolder viewHolder = new ViewHolder(rootView, this);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.txtPName.setText(this.products.get(position).getProductName());
    holder.txtDescription.setText("");
    holder.txtPrice.setText(this.products.get(position).getPrice());
    holder.numPick.setValue(Integer.parseInt(this.products.get(position).getQuantity()));
    Picasso.with(holder.imageView.getContext())
        .load(context.getResources().getString(R.string.API_BASE_URL) + this.products.get(position).getPhotoPath())
        .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    if (this.products == null) {
      return 0;
    } else {
      return this.products.size();
    }
  }

  public ArrayList<Product> getItems() {
    return this.products;
  }

  public void setItems(ArrayList<Product> products) {
    this.products = products;
    this.notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements NumberPicker.OnValueChangeListener {
    private MyRecyclerViewAdapter adapter;

    private TextView txtPName;
    private TextView txtDescription;
    private TextView txtPrice;
    private NumberPicker numPick;
    private ImageView imageView;

    public ViewHolder(View itemView, MyRecyclerViewAdapter adapter) {
      super(itemView);

      this.adapter = adapter;

      txtPName = (TextView) itemView.findViewById(R.id.textViewX1);
      txtDescription = (TextView) itemView.findViewById(R.id.textViewX2);
      txtPrice = (TextView) itemView.findViewById(R.id.textViewX3);

      numPick = (NumberPicker) itemView.findViewById(R.id.numPicker);
      numPick.setMaxValue(100);
      numPick.setMinValue(0);
      numPick.setOnValueChangedListener(this);

      imageView = (ImageView) itemView.findViewById(R.id.imageProduct1);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
      this.adapter.products.get(getAdapterPosition()).setQuantity(Integer.toString(numberPicker.getValue()));
    }
  }
}
