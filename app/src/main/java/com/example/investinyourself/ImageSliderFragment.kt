package com.example.investinyourself

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.investinyourself.databinding.FragmentImageSliderBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**SPLASH SCREEN **/
class ImageSliderFragment : Fragment() {
    lateinit var binding: FragmentImageSliderBinding
    private val sliderHandler: Handler = Handler()
    var compositePageTransformer = CompositePageTransformer()
    val sliderItems: ArrayList<SliderItems> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setList()
        imageSlider()
        bottomNavigationBar()
    }

    private fun setList() {
        sliderItems.addAll(
            arrayListOf(
                SliderItems(
                    R.drawable.meditation,
                    "Do meditation daily",
                    "Meditation",
                    getString(R.string.meditation_benefits),
                    "Meditation nourishes the mind in the same way that food nourishes the body"
                ), SliderItems(
                    R.drawable.exercise,
                    "Daily exercise",
                    "Exercise",
                    getString(R.string.exercise_benefits),
                    "Exercise should be regarded as a tribute to the heart"
                ), SliderItems(
                    R.drawable.vegetables,
                    "Eating healthy",
                    "Eat Healthy",
                    getString(R.string.eat_healthy),
                    "It is health that is real wealth and not pieces of gold and silver"
                ), SliderItems(
                    R.drawable.read_books,
                    "Reading books",
                    "Read Books",
                    getString(R.string.read_books),
                    "A reader lives a thousand lives before he dies,The man who never reads lives only one"
                ),
                SliderItems(
                    R.drawable.early,
                    "Wake-up early",
                    "Wake-up Early",
                    getString(R.string.wakeup_early),
                    "Wake up early and tackle the day before it tackles you. Be on offense, not defense"
                )
            )
        )
        binding.viewPagerImageSlider.adapter =
            SliderAdapter(sliderItems, binding.viewPagerImageSlider) { item ->
                val bundle = Bundle()
                item.image?.let { bundle.putInt("image", it) }
                bundle.putString("caption", item.caption)
                bundle.putString("heading", item.heading)
                bundle.putString("info", item.fullInfo)
                (activity as MainActivity).setFragmentOn(
                    requireContext(),
                    R.id.fragmentMain, bundle, true, DetailFragment::class.java
                )
            }
    }

    private fun imageSlider() {
        binding.viewPagerImageSlider.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        })

        binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerImageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvQuote.text = sliderItems[position].quote
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 2000)

            }
        })
    }

    private val sliderRunnable =
        Runnable {
            binding.viewPagerImageSlider.currentItem = binding.viewPagerImageSlider.currentItem + 1
        }


    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }


    fun bottomNavigationBar() {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(
                Color.BLACK, // unchecked
                Color.parseColor("#2C93C1")
            )
        )
        binding.navigation.itemIconTintList = colorStateList
        binding.navigation.itemTextColor = colorStateList

    }

}