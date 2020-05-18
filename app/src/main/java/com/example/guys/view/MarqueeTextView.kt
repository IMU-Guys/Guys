package com.example.guys.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by liujiannan on 2020-04-04
 */
class MarqueeTextView : TextView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun isFocused(): Boolean {
        return true
    }
}