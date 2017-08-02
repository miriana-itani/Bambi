package com.bambi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bambi.adapters.ListCategoryAdapter;
import com.bambi.local.Prefs;
import com.bambi.model.Category;
import com.bambi.model.CategoryResult;
import com.bambi.network.RestClient;
import com.bambi.utils.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by Miriana on 8/2/2017.
 */

public class MainPage extends BaseActivity {
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        showProgress();
        TextView login = (TextView) findViewById(R.id.login);
        if (Prefs.Companion.getInstance(getApplicationContext()).getLogin())
            login.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        final RecyclerView categories = (RecyclerView) findViewById(R.id.categories);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        categories.setLayoutManager(gridLayoutManager);
        new RestClient<CategoryResult>(getApplicationContext(), getString(R.string.base_url), "categories", CategoryResult.class) {
            @Override
            public void internetError() {
                hideProgress();
                showError(getString(R.string.internet_error));
            }

            @Override
            public void onSuccess(Object response) {
                hideProgress();
                CategoryResult categoryResult = (CategoryResult) response;
                if (categoryResult != null) {
                    categoryList = categoryResult.getData();
                    if (categoryList != null && categoryList.size() > 0) {
                        categories.setAdapter(new ListCategoryAdapter(getApplicationContext(), categoryList));
                    } else showToast(getString(R.string.error));
                }
            }

            @Override
            public void onError(String message) {
                hideProgress();
                handleError(message);
            }

            @Override
            public void onAuthentication(String message) {

            }
        };
        categories.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                if (categoryList != null && categoryList.size() > 0) {
                    Category category = categoryList.get(position);
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("id", category.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongPress(View childView, int position) {

            }
        }));
    }
}
