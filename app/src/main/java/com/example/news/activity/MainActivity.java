package com.example.news.activity;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.*;

import com.example.news.Articles;
import com.example.news.CategoryRVModel;
import com.example.news.NewsModel;
import com.example.news.R;
import com.example.news.RetroFitAPI;
import com.example.news.adapters.CategoryRVAdapter;
import com.example.news.adapters.NewsRVAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

//    e3e5ad3a1dc444f891c049e0d5d1bcd8 APIKEY

    private RecyclerView newsRV, categoryRV;
    private ProgressBar progressBar;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModel> categoryRVModels;
    private NewsRVAdapter newsRVAdapter;
    private CategoryRVAdapter categoryRVAdapter;

    private Toolbar toolbar;
    private DrawerLayout drawer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        makeActionBar();
        initView();
        sideBar();
    }

//    public void makeActionBar() {
////        toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setDisplayShowTitleEnabled(false);
//    }

    public void initView() {
        progressBar = findViewById(R.id.progressBar);
        newsRV = findViewById(R.id.recViewHorizontalNews);
        categoryRV = findViewById(R.id.recViewCategories);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);


        setSupportActionBar(toolbar);

        articlesArrayList = new ArrayList<>();
        categoryRVModels = new ArrayList<>();

        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModels, this, this::onCategoryClick);

        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        getCategory();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    public void getCategory() {
        categoryRVModels.add(new CategoryRVModel("All",
                "https://images.unsplash.com/photo-1520330979108-7d66e04b35e5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=400&q=80"));
        categoryRVModels.add(new CategoryRVModel("Technology",
                "https://images.unsplash.com/photo-1518770660439-4636190af475?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        categoryRVModels.add(new CategoryRVModel("Health",
                "https://images.unsplash.com/photo-1538333702852-c1b7a2a93001?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=752&q=80"));
        categoryRVModels.add(new CategoryRVModel("politics",
                "https://images.unsplash.com/photo-1529107386315-e1a2ed48a620?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=750&q=80"));
        categoryRVModels.add(new CategoryRVModel("Entertainment",
                "https://images.unsplash.com/photo-1603739903239-8b6e64c3b185?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=751&q=80"));
        categoryRVModels.add(new CategoryRVModel("Sports",
                "https://images.unsplash.com/photo-1517649763962-0c623066013b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        categoryRVModels.add(new CategoryRVModel("Business",
                "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
    }

    public void getNews(String category) {
        progressBar.setVisibility(View.VISIBLE);
        articlesArrayList.clear();

        String url = "https://newsapi.org/v2/top-headlines/?excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=e3e5ad3a1dc444f891c049e0d5d1bcd8";
        String categoryURL = "https://newsapi.org/v2/top-headlines?category=" + category + "&apiKey=e3e5ad3a1dc444f891c049e0d5d1bcd8&language=en";
        String BASE_URL = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroFitAPI retroFitAPI = retrofit.create(RetroFitAPI.class);
        Call<NewsModel> call;

        if (category.equals("All")) {
            call = retroFitAPI.getAllNews(url);
        } else {
            call = retroFitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                progressBar.setVisibility(View.GONE);

                ArrayList<Articles> articles = newsModel.getArticles();

                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(),
                            articles.get(i).getUrl(), articles.get(i).getUrlToImage(), articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ah Crap... failed to load news!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //the argument passed in the categoryRVAdapter class is used here.
    public void onCategoryClick(int position) {
        String category = categoryRVModels.get(position).getCategory();
        getNews(category);
    }

    public void sideBar() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //sets the drawer icon to the specified color
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

    }

}