package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ranycees.ranyview.core.CommonBackgroundStyleOperate
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit.Companion.DEVIATION_SLANTING_DOWN
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit.Companion.DEVIATION_SLANTING_TOP
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit.Companion.DEVIATION_VERTICAL
import com.ranycess.commonview.view.TextViewR

class MainActivity : AppCompatActivity() {
    private var clickTag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val views = listOf(
            findViewById<View>(R.id.click_set) as CommonBackgroundStyleOperate
        )
        findViewById<TextViewR>(R.id.click_set).setOnClickListener { view ->
            clickTag %= 11
            when (clickTag) {
                0 -> {
                    views.forEach {
                        it.setStrokeColors(
                            Color.parseColor("#A9FBCE"),
                            Color.parseColor("#BD96F3"),
                            Color.parseColor("#FB7F7F")
                        )
                    }
                }

                1 -> {
                    views.forEach {
                        it.setStrokeWidth(16f)
                    }
                }

                2 -> {
                    views.forEach {
                        it.setBackgroundColors(
                            Color.parseColor("#A9FBCE"),
                            Color.parseColor("#BD96F3"),
                            Color.parseColor("#FB7F7F")
                        )
                    }
                }

                3 -> {
                    views.forEach {
                        it.setDeviation(DEVIATION_VERTICAL)
                    }
                }

                4 -> {
                    views.forEach {
                        it.setStrokeDeviation(DEVIATION_SLANTING_DOWN)
                    }
                }

                5 -> {
                    views.forEach {
                        it.setBackgroundDeviation(DEVIATION_SLANTING_TOP)
                    }
                }

                6 -> {
                    views.forEach {
                        it.setRadius(16f)
                    }
                }

                7 -> {
                    views.forEach {
                        it.switchStroke(false)
                    }
                }

                8 -> {
                    views.forEach {
                        it.switchBackground(false)
                    }
                }

                9 -> {
                    views.forEach {
                        it.switchStroke(true)
                    }
                }

                10 -> {
                    views.forEach {
                        it.switchBackground(true)
                    }
                }
            }
            clickTag++
        }
    }
}