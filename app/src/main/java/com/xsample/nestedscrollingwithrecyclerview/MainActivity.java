package com.xsample.nestedscrollingwithrecyclerview;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.xsample.nestedscrollingwithrecyclerview.Adapter.MyAdapter;
import com.xsample.nestedscrollingwithrecyclerview.Model.Model;

import java.util.ArrayList;
import java.util.List;

import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView;

public class MainActivity extends AppCompatActivity {

    private boolean isShowingCardHeaderShadow;
    List<Model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        generateModelList();

        final MaxHeightRecyclerView rv = findViewById(R.id.card_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new MyAdapter(this,modelList));
        rv.addItemDecoration(new DividerItemDecoration(this,lm.getOrientation()));
        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrolledTop = lm.findFirstCompletelyVisibleItemPosition() ==0
                        && lm.findViewByPosition(0).getTop() == 0;
                if (!isRecyclerViewScrolledTop && !isShowingCardHeaderShadow){
                    isShowingCardHeaderShadow = true;
                    showOrHide(cardHeaderShadow,isShowingCardHeaderShadow);
                }else {
                    isShowingCardHeaderShadow =false;
                    showOrHide(cardHeaderShadow,isShowingCardHeaderShadow);
                }
            }
        });

        AdvancedNestedScrollView advancedNestedScrollView = (AdvancedNestedScrollView)findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollx, int scrolly, int oldScrollx, int oldScrolly) {
                if(scrolly ==0 && oldScrolly >0){
                    rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }

            }
        });
    }

    private void showOrHide(View view, boolean isShow) {
        view.animate().alpha(isShow?1f:0f).setDuration(100).setInterpolator(new DecelerateInterpolator());
    }

    private void generateModelList() {
        modelList.add(new Model("https://www.gstatic.com/webp/gallery/1.jpg","1 image"));
        modelList.add(new Model("https://www.gstatic.com/webp/gallery/2.jpg","2 image"));
        modelList.add(new Model("https://www.gstatic.com/webp/gallery/3.jpg","3 image"));
        modelList.add(new Model("https://www.gstatic.com/webp/gallery/1.jpg","1 image"));
        modelList.add(new Model("https://www.gstatic.com/webp/gallery/2.jpg","2 image"));
        modelList.add(new Model("https://www.gstatic.com/webp/gallery/3.jpg","3 image"));

    }
}
