package com.example.prince.vegkart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TabLayout tabLayout;
    private String Category;
    private ArrayList<ArrayList<Product>> items = new ArrayList<>();
    private RecyclerView productListView;
    private MyRecyclerViewAdapter adapter;
    private TextView textView1;
    private Button frag_button;
    ListView listView;
    Animation anim_close_fab1, anim_open_fab1, anim_open_fab2, anim_close_fab2, forward_rotate, backward_rotate;
    FloatingActionButton fab, fab1, fab2;
    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
    TextView textView2;
    Boolean isOpen = false;
    View translucentView;

    public static final String EXTRA_MESSAGE = "username";
    Intent intent;
    TextView user_name, emailView;
    String username, emailId;
    View headerView, removeView;
    Menu menu;
    MenuItem menuItem;
    LayoutInflater inflater;
    NavigationView navigationView;
    boolean inFront;

    ScaleAnimation Sin = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
    ScaleAnimation Sout = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            sendRequest();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            tabLayout = (TabLayout) findViewById(R.id.tabs);

            for (int j = 0; j < 7; j++) {
                items.add(new ArrayList<Product>());
                for (int i = 1; i < 25; i++) {
                    items.get(j).add(new Product((j + 1) + "Name" + i, "1Description" + i, String.valueOf(i), ""));
                }
            }

            productListView = (RecyclerView) findViewById(R.id.mainListView);
            productListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab1 = (FloatingActionButton)findViewById(R.id.fab1);
            fab2 = (FloatingActionButton)findViewById(R.id.fab2);
            anim_open_fab1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_open_fab1);
            anim_close_fab1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_close_fab1);
            anim_open_fab2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_open_fab2);
            anim_close_fab2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_close_fab2);
            forward_rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.forward_rotate);
            backward_rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.backward_rotate);
            fab.setOnClickListener(this);
            fab1.setOnClickListener(this);
            fab2.setOnClickListener(this);
            fadeIn.setDuration(250);
            fadeOut.setDuration(250);
            fadeIn.setFillAfter(true);
            fadeOut.setFillAfter(true);

            Sin.setDuration(450);
            Sout.setDuration(450);
            Interpolator interpolator = anim_open_fab1.getInterpolator();
            Log.d("interpolator ", ""+anim_open_fab1.getInterpolator());
            Sin.setInterpolator(interpolator);
            Sout.setInterpolator(interpolator);
            fab.startAnimation(Sin);

            textView1 = (TextView) findViewById(R.id.textView1);
            textView2 = (TextView) findViewById(R.id.textView2);
            translucentView = findViewById(R.id.translucentView);

            menu = (Menu) findViewById(R.id.drawerMenu);
            menuItem = (MenuItem) findViewById(R.id.login);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This Method sendRequest(), makes a request queue using volley.
     */
    private void sendRequest() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getString(R.string.API_BASE_URL) + getString(R.string.API_JSON_URL), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (items.size() < 1) {
                            items.add(new ArrayList<>(Arrays.asList((new Gson()).fromJson(response.getJSONArray("UArray").toString(), Product[].class))));
                            adapter.setItems(items.get(0));
                        } else {
                            items.set(0, new ArrayList<>(Arrays.asList((new Gson()).fromJson(response.getJSONArray("UArray").toString(), Product[].class))));
                            adapter.setItems(items.get(0));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void bindWidgetWithEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentCategory(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void setCurrentCategory(int tabPosition) {
        switch (tabPosition) {
            case 0:
                Category = "cate1";
                break;
            case 1:
                Category = "cate2";
                break;
            case 2:
                Category = "cate3";
                break;
            case 3:
                Category = "cate4";
                break;
            case 4:
                Category = "cate5";
                break;
        }
        adapter = new MyRecyclerViewAdapter(MainActivity.this, items.get(tabPosition));

        productListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(isOpen){
            FABanimationStop();
        }else if(inFront){
            System.exit(0);
        }else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            FragmentManager fm = getSupportFragmentManager();

            Contact_Us_Activity dFragment = new Contact_Us_Activity();
            // Show DialogFragment
            dFragment.show(fm, "Dialog Fragment");

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            FragmentManager fm = getSupportFragmentManager();

            Contact_Us_Activity dFragment = new Contact_Us_Activity();
            // Show DialogFragment
            dFragment.show(fm, "Dialog Fragment");

        } /*else if((id == R.id.login) && (menuItem.getTitle() == "Logout") ){
            LoginAct logout = new LoginAct();
            logout.onStop();
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
            user_name.setText("User Name");
            emailView.setText("Contact No");
        }else if (id == R.id.login){
            startActivity(new Intent(MainActivity.this, LoginAct.class));
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindWidgetWithEvent();

        if (tabLayout != null && tabLayout.getTabCount() == 0) {
            tabLayout.addTab(tabLayout.newTab().setText("ONE"), true);
            tabLayout.addTab(tabLayout.newTab().setText("TWO"));
            tabLayout.addTab(tabLayout.newTab().setText("THREE"));
            tabLayout.addTab(tabLayout.newTab().setText("FOUR"));
            tabLayout.addTab(tabLayout.newTab().setText("FIVE"));
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        inFront = true;
        fab.startAnimation(Sin);

        intent = getIntent();
        if(intent.hasExtra(EXTRA_MESSAGE) && intent.hasExtra("emailAddress")) {
            headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
            user_name = (TextView) headerView.findViewById(R.id.user_name);
            emailView = (TextView) headerView.findViewById(R.id.emailView);
            removeView = navigationView.getHeaderView(0);
            navigationView.removeHeaderView(removeView);

            username = intent.getStringExtra(EXTRA_MESSAGE);
            emailId = intent.getStringExtra("emailAddress");
            user_name.setText(username);
            emailView.setText(emailId);
            menuItem.setTitle("Logout");
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        inFront = false;
        fab.startAnimation(Sout);
    }

    public void FABanimationStart(){
        fab.startAnimation(forward_rotate);
        fab1.startAnimation(anim_open_fab1);
        fab2.startAnimation(anim_open_fab2);
        textView1.startAnimation(fadeIn);
        textView2.startAnimation(fadeIn);
        translucentView.startAnimation(fadeIn);
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        translucentView.setVisibility(View.VISIBLE);
        translucentView.setClickable(true);
        fab1.setClickable(true);
        fab2.setClickable(true);
        isOpen = true;
    }

    public void FABanimationStop(){
        fab.startAnimation(backward_rotate);
        fab1.startAnimation(anim_close_fab1);
        fab2.startAnimation(anim_close_fab2);
        textView1.startAnimation(fadeOut);
        textView2.startAnimation(fadeOut);
        translucentView.startAnimation(fadeOut);
        fab1.setClickable(false);
        fab2.setClickable(false);
        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        translucentView.setVisibility(View.INVISIBLE);
        translucentView.setClickable(false);
        isOpen = false;
    }

    private void FABanimation() {
        if(isOpen){
            FABanimationStop();
        }else{
            FABanimationStart();
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                FABanimation();
                break;
            case R.id.fab1:

                startActivity(new Intent(MainActivity.this,cart_activity.class));
                FABanimation();
                break;
            case R.id.fab2:

                Toast.makeText(MainActivity.this, "Search Veggkart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView1:

                Toast.makeText(MainActivity.this, "Search Veggkart", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView2:

                startActivity(new Intent(MainActivity.this,cart_activity.class));
                FABanimation();
                break;
            case R.id.translucentView:

                FABanimationStop();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("username", username);
        bundle.putString("emailId", emailId);
    }
}
