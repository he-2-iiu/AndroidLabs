package ru.mirea.chistyakov.mireaproject.ui.browser;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.mirea.chistyakov.mireaproject.R;

public class BrowserFragment extends Fragment {

    WebView web;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browser, container, false);
        web = root.findViewById(R.id.ww);
        web.setWebViewClient(new WebViewClient());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ya.ru"));
        Uri data = intent.getData();
        web.loadUrl(data.toString());
        return root;
    }

}