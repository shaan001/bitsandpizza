package com.hybrid.tech.bitsandpizza2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity  {
private String[] titles;
    private ListView drawerList;
private DrawerLayout drawerLayout;
private ActionBarDrawerToggle drawerToggle;
    private ShareActionProvider shareActionProvider;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles=getResources().getStringArray(R.array.titles);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList=(ListView)findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles));  //2nd para means the item the user clicks highlights

        drawerList.setOnItemClickListener(new DrawerItemClickListener());


    drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_drawer,R.string.close_drawer)
    {
        public void onDrawerClosed(View view)
        {
    super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }

        public void onDrawerOpened(View drawerView)
        {
    super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }


    };
    getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        if(savedInstanceState==null)
        {
            selectItem(0);
        }
    }
@Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
boolean drawerOpen=drawerLayout.isDrawerOpen(drawerList);
            menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    private void selectItem(int position)
    {
        Fragment fragment;

        switch(position) {
            case 1:
                fragment=new PizzaFragment();
                break;
            case 2:
                fragment=new PastaFragment();
                break;

            case 3:
                fragment=new StoreFragment();
                break;

            default:
                fragment=new TopFragment();
                break;


        }

        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

   private void setActionBarTitle(int position) {
       String title;

       if (position == 0) {
           title = getResources().getString(R.string.app_name);
       } else {
           title = titles[position];
       }


       getActionBar().setTitle(title);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem=menu.findItem(R.id.action_share);
        shareActionProvider=(ShareActionProvider)menuItem.getActionProvider();
        setIntent("this is a sample text");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text)

    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

@Override
protected void onPostCreate(Bundle savedInstanceState)
{
super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
}

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }
}
