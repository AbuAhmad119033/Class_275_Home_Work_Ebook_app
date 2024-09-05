package com.example.class_275_home_work_ebook_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabStory, tabPoem, tabNews;
    ProgressBar progressBar;
    ListView listView;
    HashMap<String, String>hashMap;
    ArrayList< HashMap<String,String> >arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        tabStory = findViewById(R.id.tabStory);
        tabPoem = findViewById(R.id.tabPoem);
        tabNews = findViewById(R.id.tabNews);
        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);

        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.add(R.id.frameLayout, new FirstFragment());
        fTransaction.commit();

//        String url = "https://masterbari69.000webhostapp.com/apps/complex.json";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                progressBar.setVisibility(View.GONE);
//                Log.d("ServerRes", response.toString());
//
//                try {
//                    String name = response.getString("name");
//                    String age = response.getString("age");
//
//                    textView.append(name);
//                    textView.append("\n");
//                    textView.append(age);
//
//                    JSONArray jsonArray = response.getJSONArray("video");
//
//                    for(int x = 0; x < jsonArray.length(); x++){
//                        JSONObject jsonObject = jsonArray.getJSONObject(x);
//                        String title = jsonObject.getString("title");
//                        String video_id = jsonObject.getString("video_id");
//                        textView.append("\n"+title);
//                        textView.append("\n"+video_id);
//                    }
//
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(jsonObjectRequest);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if(tabPosition == 0){
                    FragmentManager fManager = getSupportFragmentManager();
                    FragmentTransaction fTransaction = fManager.beginTransaction();
                    fTransaction.add(R.id.frameLayout, new FirstFragment());
                    fTransaction.commit();
                } else if (tabPosition == 1) {
                    FragmentManager fManager = getSupportFragmentManager();
                    FragmentTransaction fTransaction = fManager.beginTransaction();
                    fTransaction.add(R.id.frameLayout, new SecondFragment());
                    fTransaction.commit();
                } else if (tabPosition == 2) {
                    FragmentManager fManager = getSupportFragmentManager();
                    FragmentTransaction fTransaction = fManager.beginTransaction();
                    fTransaction.add(R.id.frameLayout,new ThirdFragmnet());
                    fTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item, null);

            TextView itemCover = myView.findViewById(R.id.itemCover);
            TextView itemCat = myView.findViewById(R.id.itemCat);
            TextView itemTitle = myView.findViewById(R.id.itemTitle);
            TextView itemDes = myView.findViewById(R.id.itemDes);

// i have to add same item name in php file like (Cover,Cat,Title,Des)
            hashMap = arrayList.get(position);
            String Cover = hashMap.get("Cover");
            String Cat = hashMap.get("Cat");
            String Title = hashMap.get("Title");
            String Des = hashMap.get("Des");


//            Picasso.get().load(Image_url)
//                    .placeholder(R.drawable.mypic)
//                    .into(tvCover);
            itemCat.setText(Cat);
            itemTitle.setText(Title);
            itemDes.setText(Des);

//            buttonUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

            return myView;
        }
    }

    //-----------------------------------
    private void loadData(){

        arrayList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.0.117/Apps/view.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);

                for(int x=0; x<response.length(); x++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(x);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String mobile = jsonObject.getString("mobile");
                        String email = jsonObject.getString("email");

                        hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("name", name);
                        hashMap.put("mobile", mobile);
                        hashMap.put("email", email);

                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(arrayList.size()>0){
                    MyAdapter myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ServerRes", error.toString());

            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}