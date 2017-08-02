package com.bambi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bambi.R;
import com.bambi.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Miriana on 8/2/2017.
 */

public class ListCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Category> mainModels;

    public ListCategoryAdapter(Context context, List<Category> mainModels) {
        this.context = context;
        this.mainModels = mainModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ListCategoryAdapter.ViewHeader(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHeader) {
            final ListCategoryAdapter.ViewHeader viewHeader = (ListCategoryAdapter.ViewHeader) holder;
            Category category = mainModels.get(holder.getAdapterPosition());
            if (!TextUtils.isEmpty(category.getName())) {
                viewHeader.categoryName.setText(category.getName());
            }
            if (!TextUtils.isEmpty(category.getPhoto()))
                Picasso.with(context).load(category.getPhoto()).centerInside().into(viewHeader.categoryImage);
        }
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    private class ViewHeader extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        ViewHeader(View itemView) {
            super(itemView);
            categoryImage = (ImageView) itemView.findViewById(R.id.category_image);
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
        }
    }
}
