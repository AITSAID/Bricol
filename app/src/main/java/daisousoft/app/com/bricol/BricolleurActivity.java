package daisousoft.app.com.bricol;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.HorizontalScrollView;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class BricolleurActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bricolleur);

        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(horizontalScrollView);
    }
}
