package com.smarttech.parksmart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DrawerLayout drawer;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets up the splash screen
        //setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeActivity()).commit();
        }

        //Google sign in data
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };

        if (auth.getCurrentUser() == null) {
            //closing this activity
            finish();

            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Navigation Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Nav drawer
        drawer = findViewById(R.id.drawer_layout);

        //Nav drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Nav view menu onclick events
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        selectedFragment = new ProfileActivity();
                        break;

                    case R.id.nav_gatecontrol:
                        selectedFragment = new GateControlActivity();
                        break;

                    case R.id.nav_lightcontrol:
                        selectedFragment = new LightActivity();
                        break;

                    case R.id.nav_settings:
                        selectedFragment = new SettingActivity();
                        break;

                    case R.id.nav_logout:
                        logout();
                        return true;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //Admin Visibility settings
        fStore = FirebaseFirestore.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fStore.collection("Users").document(fUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess2: " + documentSnapshot.getData());
                //Identify user access
                if (documentSnapshot.getString("isUser") != null) {
                    //if user only
                    Menu menu = navigationView.getMenu();
                    for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                        MenuItem menuItem = menu.getItem(menuItemIndex);
                        if (menuItem.getItemId() == R.id.nav_gatecontrol) {
                            menuItem.setVisible(false);
                        }
                        if (menuItem.getItemId() == R.id.nav_lightcontrol) {
                            menuItem.setVisible(false);
                        }
                    }
                } else {
                    //if admin only
                    Menu menu = navigationView.getMenu();
                    for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                        MenuItem menuItem = menu.getItem(menuItemIndex);
                        if (menuItem.getItemId() == R.id.nav_gatecontrol) {
                            menuItem.setVisible(true);
                        }
                        if (menuItem.getItemId() == R.id.nav_lightcontrol) {
                            menuItem.setVisible(true);
                        }
                    }
                }
            }
        });

        //gets current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Assigning nav_header variable for google sign in.
        //nav_header variable initialization
        ImageView uImage = navigationView.getHeaderView(0).findViewById(R.id.navHeadprofile);
        TextView uName = navigationView.getHeaderView(0).findViewById(R.id.name);
        TextView uEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

        //check validation
        if (user != null) {
            //When firebase user is not equal null, set the profile image
            Glide.with(MainActivity.this)
                    .load(user.getPhotoUrl())
                    .apply(new RequestOptions().circleCrop().centerInside())
                    .into(uImage);

            //Set name and email textView
            uName.setText(user.getDisplayName());
            uEmail.setText(user.getEmail());
        }

        //BottomNavigation OnSelect function
        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeActivity();
                    break;

                case R.id.navigation_schedule:
                    selectedFragment = new ScheduleViewActivity();
                    break;

                case R.id.navigation_availability:
                    selectedFragment = new AvailabilityActivity();
                    break;

                case R.id.navigation_direction:
                    selectedFragment = new DirectionActivity();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private void logout() {
        //Email and pass sign out
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage(R.string.DialogExitMsg);
            builder.setPositiveButton(R.string.DialogYes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finishAffinity();
                    finish();
                }
            });
            builder.setNegativeButton(R.string.DialogNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}