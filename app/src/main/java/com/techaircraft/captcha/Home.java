package com.techaircraft.captcha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;


import com.techaircraft.captcha.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityHomeBinding binding;
    DrawerLayout drawerLayout;
    NavController navController;
    View headerView;

    String total_earning, user_id, captcha_time, captcha_count, captcha_rate, auto_approve;
    int extra_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);
        headerView = binding.navigationMenu.getHeaderView(0);
        setHeaderContent();
        drawerLayout = binding.dashboard;
        setupNavigation();


        total_earning = getIntent().getStringExtra("total_earning");
        user_id = getIntent().getStringExtra("user_id");
        captcha_time = getIntent().getStringExtra("captcha_time");
        extra_time = getIntent().getIntExtra("extra_time", 2) * 1000;
        captcha_count = getIntent().getStringExtra("captcha_count");
        captcha_rate = getIntent().getStringExtra("captcha_rate");
        auto_approve = getIntent().getStringExtra("auto_approve");

        Toast.makeText(this, "total "+total_earning+user_id, Toast.LENGTH_SHORT).show();

    }

    private void setHeaderContent() {
//        gson = new Gson();
//        String userJson = SharedPrefManager.getInstance(getApplicationContext()).getUser();
//        user = gson.fromJson(userJson, User.class);
//
//        MaterialTextView name = headerView.findViewById(R.id.nameHeader);
//        name.setText(user.name);
//        MaterialTextView email = headerView.findViewById(R.id.emailHeader);
//        email.setText(user.email);
    }

    private void setupNavigation() {
        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, binding.dashboard);

        NavigationUI.setupWithNavController(binding.navigationMenu, navController);

        binding.navigationMenu.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), binding.dashboard);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    public void openPaymentProofDialog() {
//
//        LayoutInflater factory = LayoutInflater.from(this);
//        final View paymentProofDialogView = factory.inflate(R.layout.payment_proof_dialog, null);
//        final AlertDialog paymentProofDialog = new MaterialAlertDialogBuilder(Dashboard.this).create();
//        paymentProofDialog.setView(paymentProofDialogView);
//        paymentProofDialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paymentProofDialog.dismiss();
//            }
//        });
//
//        paymentProofDialogView.findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/earneasy24/"));
//                startActivity(browser);
//            }
//        });
//
//        paymentProofDialogView.findViewById(R.id.twitter).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Earn_easy24"));
//                startActivity(browser);
//            }
//        });
//
//        /* paymentProofDialogView.findViewById(R.id.youtube).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCvkDs2qrTCfpSbil_C1OdNA"));
//                startActivity(browser);
//            }
//        });*/
//
//        paymentProofDialogView.findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/earneasy24/"));
//                startActivity(browser);
//            }
//        });
//        paymentProofDialogView.findViewById(R.id.telegram).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Earneasys24/"));
//                startActivity(browser);
//            }
//        });
//
//        paymentProofDialog.show();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id) {

            case R.id.home:
                navController.navigate(R.id.homeFragment);
                break;

            case R.id.view_messages:
                navController.navigate(R.id.viewMessagesFragment);
                break;

            case R.id.order_history:
                navController.navigate(R.id.orderHistoryFragment);
                break;

            case R.id.logout:
                drawerLayout.closeDrawer(0);
                break;


//            case R.id.plans:
//                navController.navigate(R.id.plansFragment);
//                break;
//
//            case R.id.myAccount:
//                navController.navigate(R.id.myAccountFragment);
//                break;
//            case R.id.faqs:
//                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.earneasy24.com/faqs/"));
//                startActivity(browser);
//                break;
//            case R.id.support:
//
//                new MaterialAlertDialogBuilder(Dashboard.this, R.style.ThemeOverlay_App_MaterialAlertDialog)
//                        .setMessage(getResources().getString(R.string.support_message))
//                        .setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
//                            dialog.dismiss();
//                        })
//                        .show();
//                break;
//            case R.id.howItWorks:
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.earneasy24.com/how-it-works"));
//                startActivity(browser);
//                break;
//            case R.id.share:
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT,
//                        "Hey check out this amazing app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
//                break;
//            case R.id.paymentProof:
//
//                openPaymentProofDialog();
//
//                break;
//            case R.id.logout:
//                SharedPrefManager.getInstance(getApplicationContext()).clear();
//                FirebaseAuth.getInstance().signOut();
//
//                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build();
//
//                GoogleSignIn.getClient(getApplicationContext(), gso).signOut().addOnCompleteListener(this,
//                        new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
//                                finishAffinity();
//                                Intent intent = new Intent(getApplicationContext(), Onboarding.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        });
//                break;
        }
        return true;
    }

//    @Override
//    public void onPaymentSuccess(String s) {
//
//        try {
//            Call<SetPlansResponse> call = RetrofitClient.getInstance(Dashboard.this).getApi().setPlan(s);
//            call.enqueue(new Callback<SetPlansResponse>() {
//                @Override
//                public void onResponse(Call<SetPlansResponse> call, Response<SetPlansResponse> response) {
//
//                    if (response.code() == 200 && response.body() != null) {
//                        User user = response.body().getUser();
//                        Plan plan = response.body().getPlan();
//
//                        Gson gson = new Gson();
//                        String userJson = gson.toJson(user);
//                        String planJson = gson.toJson(plan);
//
//                        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(Dashboard.this);
//                        sharedPrefManager.saveUser(userJson);
//                        sharedPrefManager.savePlan(planJson);
//
//                        new AlertDialog.Builder(Dashboard.this)
//                                .setMessage(getResources().getString(R.string.payment_success))
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .create()
//                                .show();
//
//
//                    } else {
//                        new AlertDialog.Builder(Dashboard.this)
//                                .setMessage("Something went wrong, please try again")
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .create()
//                                .show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<SetPlansResponse> call, Throwable t) {
//
//                }
//            });
//        } catch (Exception e) {
//            Log.d("nirajkr", e.getMessage());
//        }
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Toast.makeText(Dashboard.this, "Error in payment. Please try again.", Toast.LENGTH_SHORT).show();
//    }

}
