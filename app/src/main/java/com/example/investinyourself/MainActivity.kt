package com.example.investinyourself

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.investinyourself.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    fun initView() {
        startFragment()
    }

    private fun startFragment() {
        setFragmentOn(
            this,
            R.id.fragmentMain, null, false, ImageSliderFragment::class.java
        )
    }

    override fun onBackPressed() {
        Log.i(TAG, "onBackPressed")
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        super.onBackPressed()
    }

    fun setFragmentOn(
        context: Context,
        replaceViewId: Int,
        bundle: Bundle?,
        backPressed: Boolean,
        fragmentClass: Class<out Fragment>,
        frag: Fragment? = null
    ) {
        try {

            val fragment = frag ?: fragmentClass.newInstance()
            if (bundle != null) fragment.arguments = bundle
            val manager = (context as AppCompatActivity).supportFragmentManager
            val transaction =
                manager.beginTransaction()
            transaction.add(replaceViewId, fragment)
            if (backPressed) transaction.addToBackStack(null)
            transaction.commit()
        } catch (ex: Exception) {
            Log.i(TAG, "setFragmentOn($ex)")
        }
    }

}