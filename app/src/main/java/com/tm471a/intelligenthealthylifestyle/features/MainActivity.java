package com.tm471a.intelligenthealthylifestyle.features;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tm471a.intelligenthealthylifestyle.LauncherActivity;
import com.tm471a.intelligenthealthylifestyle.R;
import com.tm471a.intelligenthealthylifestyle.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        auth = FirebaseAuth.getInstance();

        // Check authentication state
        if (auth.getCurrentUser() == null) {
            redirectToLauncher();
            return;
        }
//        addSampleWorkoutPlan();
//        addSampleWorkoutPlan();
//        addSampleWorkoutPlan();
//        addSampleWorkoutPlan();

        setupNavigation();
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Set up Bottom Navigation
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_workout,
                R.id.nav_nutrition,
                R.id.nav_progress,
                R.id.nav_assistant,
                R.id.nav_profile
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    private void redirectToLauncher() {
        startActivity(new Intent(this, LauncherActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        auth.signOut();
        redirectToLauncher();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void addSampleWorkoutPlan() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        // Create sample exercise
        Map<String, Object> exercise1 = new HashMap<>();
        exercise1.put("name", "Push-ups");
        exercise1.put("primary_muscles", Arrays.asList("Chest", "Triceps"));
        exercise1.put("equipment", Collections.singletonList("None"));
        exercise1.put("sets", 3);
        exercise1.put("reps", 12);

        // Create workout plan document
        Map<String, Object> workoutPlan = new HashMap<>();
        workoutPlan.put("plan_name", "Beginner Strength");
        workoutPlan.put("duration", "4 Weeks");
        workoutPlan.put("difficulty", "Beginner");
        workoutPlan.put("exercises", Arrays.asList(exercise1));

        // Add to Firestore
        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(user.getUid())
                .collection("workout_plans")
                .add(workoutPlan)
                .addOnSuccessListener(documentReference ->
                        Log.d("FIREBASE", "Added sample workout plan"))
                .addOnFailureListener(e ->
                        Log.e("FIREBASE", "Error adding plan", e));
    }
}
