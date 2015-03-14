package Helper;

import android.widget.AbsListView;

/**
 * Created by APPLE on 1/10/14.
 */
public abstract class LoadMoreScrollListener implements AbsListView.OnScrollListener {

    private boolean loading = false;
    public Integer page = 1;
    public boolean stop = false;
    public abstract void onLoadMore(Integer page);

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (loading && (totalItemCount == firstVisibleItem+visibleItemCount)){
            loading = false;
            onLoadMore(page++);
        }
    }

    public void ready(){
        loading = !stop;
    }

    public void reset(){
        page = 1;
        loading = false;
    }

    public boolean isReady() {
        return loading;
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}