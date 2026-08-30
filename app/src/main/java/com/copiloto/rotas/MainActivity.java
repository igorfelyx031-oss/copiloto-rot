package com.copiloto.rotas;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request) {

                return openExternalUrl(request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url) {

                return openExternalUrl(url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(
                    String origin,
                    GeolocationPermissions.Callback callback) {

                if (checkSelfPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    callback.invoke(origin, true, false);

                } else {

                    requestPermissions(
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            },
                            100
                    );
                }
            }
        });

        if (checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    100
            );
        }

        webView.loadUrl(
                "https://igorfelyx031-oss.github.io/copiloto-rot/"
        );

        setContentView(webView);
    }

    private boolean openExternalUrl(String url) {

        if (url == null || url.isEmpty()) {
            return false;
        }

        // Abre links intent:// no aplicativo externo,
        // como o Google Maps.
        if (url.startsWith("intent://")) {

            try {

                Intent intent = Intent.parseUri(
                        url,
                        Intent.URI_INTENT_SCHEME
                );

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

                return true;

            } catch (ActivityNotFoundException e) {

                try {

                    Intent fallback = Intent.parseUri(
                            url,
                            Intent.URI_INTENT_SCHEME
                    );

                    String fallbackUrl =
                            fallback.getStringExtra(
                                    "browser_fallback_url"
                            );

                    if (fallbackUrl != null
                            && !fallbackUrl.isEmpty()) {

                        startActivity(
                                new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(fallbackUrl)
                                )
                        );
                    }

                } catch (Exception ignored) {
                }

                return true;

            } catch (Exception e) {

                return true;
            }
        }

        // Também permite links de navegação direta.
        if (url.startsWith("geo:")
                || url.startsWith("google.navigation:")) {

            try {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                );

                intent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                );

                startActivity(intent);

                return true;

            } catch (ActivityNotFoundException e) {

                return true;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {

            webView.goBack();

        } else {

            super.onBackPressed();
        }
    }
}
