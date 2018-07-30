package com.example.cibilcheck;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Document;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static WebView webView;
    private TextView dialogTV;
    private Animation animationFadeIn;
    private Animation animationFadeOut;

    //static methods with handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    mCanGoBack();
                    mGoBack();
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webView=findViewById(R.id.cibil_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());

        dialogTV = (TextView) findViewById(R.id.dialogTV);
        dialogTV.setVisibility(View.INVISIBLE);

        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (animation == animationFadeIn) {
                    dialogTV.startAnimation(animationFadeOut);
                    dialogTV.startAnimation(animationFadeIn);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

      //  cibil_wv.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://www.wishfin.com/credit-score/how-to-check-cibil-score-on-whatsapp/");

        //row whatsappMainDiv

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("out")) { // Could be cleverer and use a regex
                view.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
                String temp = "https://www.retailmenot.com/showcoupon/";
                String[] separated = url.split("/");
                view.loadUrl(temp + separated[separated.length - 1] + "/");
                return true; // Leave webview and use browser
            } else {
                view.loadUrl(url);
                return true;
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            dialogTV.setVisibility(View.VISIBLE);
            dialogTV.startAnimation(animationFadeIn);
            webView.setVisibility(View.INVISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            webView.goBack();
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            injectCSS();
            webView.getSettings().setUserAgentString("");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogTV.setVisibility(View.INVISIBLE);
                    webView.setVisibility(View.VISIBLE);
                }
            }, 1000);
            super.onPageFinished(view, url);
        }

    }

    public static boolean mCanGoBack() {
        return webView.canGoBack();

    }

    public static void mGoBack() {
        webView.goBack();
    }

    private void injectCSS() {
        try {

//            webView.loadUrl("javascript:(function() { " +
//                    "var head = document.getElementsByTagName('footer')[0];"
//                    + "head.parentNode.removeChild(head);" +
//                    "})()");
//            webView.loadUrl("javascript:(function() { " +
//                    "var head = document.getElementsByTagName('p')[2];"
//                    + "head.parentNode.removeChild(head);" +
//                    "})()");
//            webView.loadUrl("javascript:(function() { " +
//                    "var head = document.getElementsByTagName('p')[3];"
//                    + "head.parentNode.removeChild(head);" +
//                    "})()");
//            webView.loadUrl("javascript:(function() { " +
//                    "var head = document.getElementsByTagName('p');"
//                    + "head.parentNode.removeChild(head);" +
//                    "})()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('row category-row')[0].style.display='none'; })()");
//
//
//            webView.loadUrl("javascript:(function() { " +
//                    "var head = document.getElementsByTagName('h1')[0];"
//                    + "head.parentNode.removeChild(head);" +
//                    "})()");
//            webView.loadUrl("javascript:(function() { " +
//                    "var head = document.getElementsByTagName('h1')[2];"
//                    + "head.parentNode.removeChild(head);" +
//                    "})()");
//
//
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('row category-row')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('col-xs-12')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('article-sub-info')[0].style.display='none'; })()");
//
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('row sub-header')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('col-sm-6')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('col-sm-6')[1].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('addtoany_share_save_container addtoany_content addtoany_content_top')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('post-thumbnail img-responsive')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('container')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('emicalc_widget')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('img-responsive')[3].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('table-responsive blog-table')[0].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('table-responsive blog-table')[1].style.display='none'; })()");
//            webView.loadUrl("javascript:(function() { " +
//                    "document.getElementsByClassName('widget-sec')[0].style.display='none'; })()");
            InputStream inputStream = getApplicationContext().getAssets().open("style.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            //widget-sec
            webView.loadUrl("javascript:(function() {" +
                    "var head = document.getElementsByTagName('footer')[0];head.parentNode.removeChild(head);"+
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }//blog-container
    }
}
/*.row{display: none;}
.container{display: none;}*/

/*
  Cibil_wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                Cibil_wv.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('footer')[0];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");
                // hide element by class name

                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('row category-row')[0].style.display='none'; })()");

                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('addtoany_share_save_container addtoany_content addtoany_content_top')[0].style.display='none'; })()");

                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('post-thumbnail img-responsive')[0].style.display='none'; })()");

                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('container')[0].style.display='none'; })()");
                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('container')[3].style.display='none'; })()");
                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('container')[4].style.display='none'; })()");
                //col-xs-12
                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('widget-sec')[0].style.display='none'; })()");
                //
                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('emicalc_widget')[0].style.display='none'; })()");

                Cibil_wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('img-responsive')[3].style.display='none'; })()");
//                Cibil_wv.loadUrl("javascript:(function() { " +
//                        "document.getElementById('your_id').style.display='none';})()");
            }
        });

        Cibil_wv.loadUrl("https://www.wishfin.com/credit-score/how-to-check-cibil-score-on-whatsapp/");
*/