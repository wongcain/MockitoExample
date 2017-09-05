package com.example.cain.mockitoexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IMainView {

    private TextView textView;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(new Datasource(), PersistanceUtil.getInstance(this));

        textView = (TextView) findViewById(R.id.text);

        findViewById(R.id.sync_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doSync();
            }
        });

        findViewById(R.id.async_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doAsync();
            }
        });

        findViewById(R.id.persist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doAsyncAndPersist();
            }
        });

        presenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
