package cn.com.avatek.pole;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**

    2.在XML布局文件中添加 SmartRefreshLayout
<com.scwang.smartrefresh.layout.SmartRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/refreshLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <android.support.v7.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:overScrollMode="never"
    android:background="#fff" />
</com.scwang.smartrefresh.layout.SmartRefreshLayout>
            3.在 Activity 或者 Fragment 中添加代码

    RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
refreshLayout.setOnRefreshListener(new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            refreshlayout.finishRefresh(2000);//传入false表示刷新失败
        }
    });
refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
        @Override
        public void onLoadMore(RefreshLayout refreshlayout) {
            refreshlayout.finishLoadMore(2000);//传入false表示加载失败
        }
    });
     *
     */
}
