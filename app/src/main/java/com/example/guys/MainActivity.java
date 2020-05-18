package com.example.guys;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guys.util.ImageUtilKt;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Drawable drawable = this.getDrawable(R.drawable.bg_dialog);
        ((ImageView)this.findViewById(R.id.img)).setImageBitmap(ImageUtilKt.rsBlur(this, ImageUtilKt.drawableToBitmap(drawable), 15));
    }
}
