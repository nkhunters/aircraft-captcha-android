package com.techaircraft.captcha;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.techaircraft.captcha.Api.RetrofitClient;
import com.techaircraft.captcha.Models.Captcha;
import com.techaircraft.captcha.Models.DefaultResponse;
import com.google.android.material.snackbar.Snackbar;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptchaActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.captcha_image)
    ImageView captcha_image;
    @BindView(R.id.captcha_text)
    EditText captcha_text;
    @BindView(R.id.btn_continue)
    Button btn_continue;
    @BindView(R.id.btn_skip)
    Button btn_skip;
    @BindView(R.id.right_captcha_count)
    TextView txt_right_count;
    @BindView(R.id.wrong_captcha_count)
    TextView txt_wrong_count;
    @BindView(R.id.skip_captcha_count)
    TextView txt_skip_count;
    @BindView(R.id.captcha_type)
    TextView captcha_type;
    @BindView(R.id.timer_text)
    TextView timer_text;
    @BindView(R.id.next_order)
    Button next_order;
    @BindView(R.id.balance)
    TextView balance;
    TextView txt_user_id, txt_total_earning;

    androidx.appcompat.app.AlertDialog alertDialog = null;

    //int position = 0, right_count, wrong_count, skip_count, total_earning, auto_approve;
    ArrayList<Captcha> dataList;
    //String image_url = "http://captchabro.website/CaptchaApi/includes/uploads/";
    CountDownTimer timer, imageDelayTimer;
    boolean timer_running = false;
    String captcha_id;
    String str_captcha_type;

    String total_earning, user_id, captcha_time, captcha_count, captcha_rate, auto_approve;
    int extra_time;

    CircularProgressDrawable circularProgressDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        txt_user_id = toolbar.findViewById(R.id.user_id);
        txt_total_earning = toolbar.findViewById(R.id.total_earning);

        total_earning = getIntent().getStringExtra("total_earning");
        user_id = getIntent().getStringExtra("user_id");
        captcha_time = getIntent().getStringExtra("captcha_time");
        extra_time = getIntent().getIntExtra("extra_time", 2) * 1000;
        captcha_count = getIntent().getStringExtra("captcha_count");
        captcha_rate = getIntent().getStringExtra("captcha_rate");
        auto_approve = getIntent().getStringExtra("auto_approve");

        txt_user_id.setText(user_id);
        txt_total_earning.setText("Total Earning - " + total_earning + " $");
        balance.setText(captcha_count + " / " + captcha_rate + " $");

        next_order.setEnabled(false);

        circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        getCaptcha();

        btn_continue.setOnClickListener(this);
        btn_skip.setOnClickListener(this);
        next_order.setOnClickListener(this);
    }

    private void getCaptcha() {

        btn_skip.setEnabled(false);
        btn_continue.setEnabled(false);

        final ProgressDialog dialog = new ProgressDialog(CaptchaActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();

        final Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call = RetrofitClient.getInstance().getApi().getCaptcha(user_id);
        call.enqueue(new Callback<com.techaircraft.captcha.ModelsNew.CaptchaResponse>() {
            @Override
            public void onResponse(Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call, Response<com.techaircraft.captcha.ModelsNew.CaptchaResponse> response) {

                dialog.dismiss();
                com.techaircraft.captcha.ModelsNew.Captcha captcha = response.body().getCaptcha();
                if (captcha != null) {

                    String str_right_count = captcha.getRight_count();
                    String str_wrong_count = captcha.getWrong_count();
                    String str_skip_count = captcha.getSkip_count();
                    
                    captcha_id = captcha.getId();
                    String str_image = captcha.getImage();
                    str_captcha_type = captcha.getCaptcha_type();

                    if (Integer.parseInt(str_right_count) >= Integer.parseInt(captcha_count) && auto_approve.equals("0")) {

                        completeOrder();
                    } else if (Integer.parseInt(str_right_count) >= Integer.parseInt(captcha_count) && auto_approve.equals("1")) {
                        autoApproveOrder();
                    } else {
                        txt_right_count.setText(str_right_count);
                        txt_wrong_count.setText(str_wrong_count);
                        txt_skip_count.setText(str_skip_count);
                        captcha_type.setText(str_captcha_type);


                        Glide.with(CaptchaActivity.this)
                                .load(str_image)
                                .placeholder(circularProgressDrawable)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        btn_skip.setEnabled(true);
                                        btn_continue.setEnabled(true);

                                        if (timer_running) {

                                            timer.cancel();
                                            startTimer();
                                        } else
                                            startTimer();
                                        return false;
                                    }
                                })
                                .into(captcha_image);
                    }
                }
            }

            @Override
            public void onFailure(Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call, Throwable t) {

                dialog.dismiss();
                Log.d("niraj", "Failed");
            }
        });
    }

    private void submitCaptcha() {

        btn_skip.setEnabled(false);
        btn_continue.setEnabled(false);

        final ProgressDialog dialog = new ProgressDialog(CaptchaActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        final Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call = RetrofitClient.getInstance().getApi().submitCaptcha(user_id, captcha_id, captcha_text.getText().toString().trim(), str_captcha_type);
        call.enqueue(new Callback<com.techaircraft.captcha.ModelsNew.CaptchaResponse>() {
            @Override
            public void onResponse(Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call, Response<com.techaircraft.captcha.ModelsNew.CaptchaResponse> response) {

                dialog.dismiss();
                com.techaircraft.captcha.ModelsNew.Captcha captcha = response.body().getCaptcha();
                if (captcha != null) {

                    captcha_text.setText("");
                    String str_right_count = captcha.getRight_count();
                    String str_wrong_count = captcha.getWrong_count();
                    String str_skip_count = captcha.getSkip_count();
                    String is_right = captcha.getIs_right();
                    int extra_time = Integer.parseInt(captcha.getExtra_time()) * 1000;
                    captcha_id = captcha.getId();
                    String str_image = captcha.getImage();
                    str_captcha_type = captcha.getCaptcha_type();

                    if (is_right.equals("0"))
                        showSnackBar("Wrong Answer");

                    if (Integer.parseInt(str_right_count) >= Integer.parseInt(captcha_count) && auto_approve.equals("0")) {

                        completeOrder();
                    } else if (Integer.parseInt(str_right_count) >= Integer.parseInt(captcha_count) && auto_approve.equals("1")) {
                        autoApproveOrder();
                    } else {
                        txt_right_count.setText(str_right_count);
                        txt_wrong_count.setText(str_wrong_count);
                        txt_skip_count.setText(str_skip_count);
                        captcha_type.setText(str_captcha_type);

                        if (imageDelayTimer != null)
                            imageDelayTimer.cancel();

                        imageDelayTimer = new CountDownTimer(extra_time, 1000) {
                            public void onFinish() {
                                // When timer is finished
                                // Execute your code here
                                Glide.with(CaptchaActivity.this)
                                        .load(str_image)
                                        .placeholder(circularProgressDrawable)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                btn_skip.setEnabled(true);
                                                btn_continue.setEnabled(true);
                                                if (timer_running) {

                                                    timer.cancel();
                                                    startTimer();
                                                } else
                                                    startTimer();
                                                return false;
                                            }
                                        })
                                        .into(captcha_image);
                            }

                            public void onTick(long millisUntilFinished) {
                                // millisUntilFinished    The amount of time until finished.
                                captcha_image.setImageDrawable(circularProgressDrawable);
                            }
                        }.start();



                        /*Picasso.get().load(image_url + str_image).resize(500, 200).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(captcha_image, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                                if (timer_running) {

                                    timer.cancel();
                                    startTimer();
                                } else
                                    startTimer();
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });*/
                    }
                }
            }

            @Override
            public void onFailure(Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call, Throwable t) {

                dialog.dismiss();
                Log.d("niraj", "Failed");
            }
        });
    }

    private void skipCaptcha() {
        btn_skip.setEnabled(false);
        btn_continue.setEnabled(false);
        final ProgressDialog dialog = new ProgressDialog(CaptchaActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        final Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call = RetrofitClient.getInstance().getApi().skipCaptcha(user_id, captcha_id, str_captcha_type);
        call.enqueue(new Callback<com.techaircraft.captcha.ModelsNew.CaptchaResponse>() {
            @Override
            public void onResponse(Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call, Response<com.techaircraft.captcha.ModelsNew.CaptchaResponse> response) {

                dialog.dismiss();
                com.techaircraft.captcha.ModelsNew.Captcha captcha = response.body().getCaptcha();
                if (captcha != null) {

                    captcha_text.setText("");
                    String str_right_count = captcha.getRight_count();
                    String str_wrong_count = captcha.getWrong_count();
                    String str_skip_count = captcha.getSkip_count();
                    String is_right = captcha.getIs_right();
                    captcha_id = captcha.getId();
                    String str_image = captcha.getImage();
                    str_captcha_type = captcha.getCaptcha_type();

                    if (Integer.parseInt(str_right_count) >= Integer.parseInt(captcha_count) && auto_approve.equals("0")) {

                        completeOrder();
                    } else if (Integer.parseInt(str_right_count) >= Integer.parseInt(captcha_count) && auto_approve.equals("1")) {
                        autoApproveOrder();
                    } else {
                        txt_right_count.setText(str_right_count);
                        txt_wrong_count.setText(str_wrong_count);
                        txt_skip_count.setText(str_skip_count);
                        captcha_type.setText(str_captcha_type);

                        if (imageDelayTimer != null)
                            imageDelayTimer.cancel();

                        imageDelayTimer = new CountDownTimer(extra_time, 1000) {
                            public void onFinish() {

                                Glide.with(CaptchaActivity.this)
                                        .load(str_image)
                                        .placeholder(circularProgressDrawable)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                                btn_skip.setEnabled(true);
                                                btn_continue.setEnabled(true);
                                                if (timer_running) {

                                                    timer.cancel();
                                                    startTimer();
                                                } else
                                                    startTimer();
                                                return false;
                                            }
                                        })
                                        .into(captcha_image);
                            }

                            public void onTick(long millisUntilFinished) {
                                // millisUntilFinished    The amount of time until finished.
                                captcha_image.setImageDrawable(circularProgressDrawable);
                            }
                        }.start();

                    }
                }
            }

            @Override
            public void onFailure(Call<com.techaircraft.captcha.ModelsNew.CaptchaResponse> call, Throwable t) {

                dialog.dismiss();
                Log.d("niraj", "Failed");
            }
        });
    }

    private void autoApproveOrder() {

        btn_continue.setEnabled(false);
        btn_skip.setEnabled(false);
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().autoApproveOrder(user_id);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                if (response.code() == 201) {

                    Snackbar.make(findViewById(R.id.main_layout), "Your " + captcha_rate + "$ payment completed successfully. Please login again to continue", Snackbar.LENGTH_LONG).show();
                    new Handler()
                            .postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }, 5000);

                } else {
                    showSnackBar("Something went wrong. Please try again");
                    finish();
                    Log.d("niraj", response.message());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                Log.d("niraj", "Failed");
                Log.d("niraj", t.getMessage());
                showSnackBar("Something went wrong. Please try again");
                finish();
            }
        });
    }

    private void completeOrder() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CaptchaActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Your " + captcha_rate + " $ payment completed. You can now take payment from company and Click on Next order button to take next order");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                dialog.cancel();
                next_order.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                next_order.setEnabled(true);
                btn_continue.setEnabled(false);
                btn_skip.setEnabled(false);
                captcha_text.setEnabled(false);
                btn_continue.setBackgroundColor(getResources().getColor(R.color.light_grey));
                btn_skip.setBackgroundColor(getResources().getColor(R.color.light_grey));
            }
        }).create().show();
    }

    private void startTimer() {

        timer_running = true;
        int int_timer = Integer.parseInt(captcha_time);

        timer = new CountDownTimer((int_timer + 1) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timer_text.setText(String.valueOf((millisUntilFinished / 1000)) + " s");
            }

            @Override
            public void onFinish() {

                skipCaptcha();
                //getCaptcha();
            }
        };
        timer.start();
    }

    /*private void getNewCaptcha() {

        if (position >= dataList.size()) {

            position = 0;
            Collections.shuffle(dataList);
        }

        if (right_count >= Integer.parseInt(captcha_count) && auto_approve == 0) {

            completeOrder();
        } else if (right_count >= Integer.parseInt(captcha_count) && auto_approve == 1) {
            autoApproveOrder();
        } else {

            Picasso.get().load(image_url + dataList.get(position + 1).getImage()).resize(500, 200).into(captcha_image, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    if (timer_running) {

                        timer.cancel();
                        startTimer();
                    } else
                        startTimer();
                }

                @Override
                public void onError(Exception e) {

                }
            });
            captcha_type.setText("* " + dataList.get(position + 1).getCaptcha_type());
            position++;
        }

    }
*/
    @Override
    public void onClick(View v) {

        if (v == next_order) {

            final ProgressDialog progressDialog = new ProgressDialog(CaptchaActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Call<DefaultResponse> call1 = RetrofitClient.getInstance().getApi().createNextOrder(user_id);
            call1.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    progressDialog.cancel();
                    if (response.code() == 201) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CaptchaActivity.this);
                        builder.setCancelable(false);
                        builder.setMessage("Your request for next order has been sent. Please wait for response");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                                next_order.setEnabled(false);
                            }
                        }).create().show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    progressDialog.cancel();
                }
            });
        }

        if (v == btn_continue) {

            submitCaptcha();
        }

        if (v == btn_skip) {

            skipCaptcha();
        }
    }


    public void showSnackBar(String msg) {

        Snackbar mSnackBar = Snackbar.make(findViewById(R.id.main_layout), msg, Snackbar.LENGTH_SHORT);
        View view = mSnackBar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.RED);
        TextView mainTextView = (TextView) (view).findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mSnackBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.order_history:
                if (timer_running)
                    timer.cancel();
                Intent intent = new Intent(CaptchaActivity.this, OrderHistory.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("total_earning", total_earning);
                startActivity(intent);
                return true;
            case R.id.view_messages:
                if (timer_running)
                    timer.cancel();
                startActivity(new Intent(CaptchaActivity.this, ViewMessages.class));
                return true;
            case R.id.logout:
                if (timer_running)
                    timer.cancel();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer_running)
            timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer_running)
            timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null)
            timer.start();

    }
}
