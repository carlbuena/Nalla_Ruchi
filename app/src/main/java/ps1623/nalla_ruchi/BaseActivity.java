package ps1623.nalla_ruchi;


import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        // This is going to be our actual root layout.
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        /**
         * Note that we don't pass the child's layoutId to the parent,
         * instead we pass it our inflated layout.
         */
        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        if (useToolbar())
        {
            setSupportActionBar(toolbar);
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }

        setUpNavView();
    }

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     * @return true
     */
    protected boolean useToolbar()
    {
        return true;
    }

    protected void setUpNavView()
    {
        navigationView.setNavigationItemSelectedListener(this);

        if( useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close);

            fullLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
                    .getDrawable(this, R.drawable.ic_drawer));
        }
    }

    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     * @return
     */
    protected boolean useDrawerToggle()
    {
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fullLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();

        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.cook_home:
                startActivity(new Intent(this, CookHome.class));
                return true;

            case R.id.customer_home:
                startActivity(new Intent(this, CustomerHome.class));
                return true;

            case R.id.cook_profile:
                startActivity(new Intent(this, CookProfile.class));
                return true;

            case R.id.search:
                startActivity(new Intent(this, Search.class));
                return true;

            case R.id.add_food:
                startActivity(new Intent(this, InsertFood.class));
                return true;

            case R.id.view_food:
                startActivity(new Intent(this, ViewAllFood.class));
                return true;

            case R.id.view_customers:
                startActivity(new Intent(this, ViewAllCustomers.class));
                return true;

            case R.id.view_cooks:
                startActivity(new Intent(this, ViewAllCooks.class));
                return true;

            case R.id.view_map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;

            case R.id.food_gallery:
                startActivity(new Intent(this, foodGalleryMain.class));
                return true;

            case R.id.view_bookings:
                //TODO: make viewbookings class
                //startActivity(new Intent(this, ViewBookings.class));
                return true;

            case R.id.make_booking:
                startActivity(new Intent(this, Booking.class));
                return true;

            case R.id.logout:
                startActivity(new Intent(this, HomePage.class));
                return true;

            case R.id.customer_profile:
                startActivity(new Intent(this, CustomerProfile.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}