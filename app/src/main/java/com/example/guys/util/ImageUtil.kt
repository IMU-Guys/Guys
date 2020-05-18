package com.example.guys.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur


/**
 * Created by liujiannan on 2020-05-18
 */
public fun rsBlur(context: Context?, source: Bitmap, radius: Int): Bitmap? {
    //(1)
    val renderScript = RenderScript.create(context)

    // Allocate memory for Renderscript to work with
    //(2)
    val input = Allocation.createFromBitmap(renderScript, source)
    val output = Allocation.createTyped(renderScript, input.type)
    //(3)
    // Load up an instance of the specific script that we want to use.
    val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    //(4)
    scriptIntrinsicBlur.setInput(input)
    //(5)
    // Set the blur radius
    scriptIntrinsicBlur.setRadius(radius.toFloat())
    //(6)
    // Start the ScriptIntrinisicBlur
    scriptIntrinsicBlur.forEach(output)
    //(7)
    // Copy the output to the blurred bitmap
    output.copyTo(source)
    //(8)
    renderScript.destroy()
    return source
}

public fun drawableToBitmap(drawable: Drawable): Bitmap {
    val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    drawable.draw(canvas)
    return bitmap
}
