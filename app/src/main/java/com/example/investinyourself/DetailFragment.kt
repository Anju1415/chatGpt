package com.example.investinyourself

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.investinyourself.databinding.FragmentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    var sliderItem: SliderItems? = null
    var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null
    val TAG = "DetailFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.bottomSheetBehaviorId.llBottomSheet)
        initView()
    }

    fun initView() {
        handleData()
        val backgroundTint = ContextCompat.getColor(requireContext(), R.color.fab2)
        binding.fbBack.backgroundTintList = ColorStateList.valueOf(backgroundTint)
        binding.fbBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        // Get the screen density of the device
        // Get the screen density of the device
        val density = resources.displayMetrics.density
        val pixels = (40 * density + 0.5f).toInt()


        bottomSheetBehavior?.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Handle different states of the bottom sheet
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // The bottom sheet is collapsed
                        /*val backgroundTint = ContextCompat.getColor(requireContext(), R.color.fab2)
                        binding.fbBack.backgroundTintList = ColorStateList.valueOf(backgroundTint)*/
                        bottomSheetBehavior?.setPeekHeight(pixels)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // The bottom sheet is expanded
                        val h = binding.appbar.height
                        Log.i(TAG, "height: $h")
                        bottomSheetBehavior?.setPeekHeight(h)
                      /*  val backgroundTint = ContextCompat.getColor(requireContext(), R.color.fab)
                        binding.fbBack.backgroundTintList = ColorStateList.valueOf(backgroundTint)*/
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // The bottom sheet is hidden
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        // The user is dragging the bottom sheet
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        // The bottom sheet is settling into a new state
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < 0) {
                    // The bottom sheet is sliding down
                    val backgroundTint = ContextCompat.getColor(requireContext(), R.color.fab2)
                   /* binding.fbBack.backgroundTintList = ColorStateList.valueOf(backgroundTint)
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED*/
                } else if (
                    slideOffset > 0
                ) {
                    /*val backgroundTint = ContextCompat.getColor(requireContext(), R.color.fab)
                    binding.fbBack.backgroundTintList = ColorStateList.valueOf(backgroundTint)*/
                    //bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })
        binding.bottomSheetBehaviorId.cvYes.setOnClickListener {
            val backgroundTint = ContextCompat.getColor(requireContext(), R.color.fab2)
            binding.fbBack.backgroundTintList = ColorStateList.valueOf(backgroundTint)
            binding.bottomSheetBehaviorId.llBottomSheet.visibility = View.GONE
            playAnimationBlast()
        }
        binding.bottomSheetBehaviorId.cvNo.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.bottomSheetBehaviorId.llBottomSheet.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun handleData() {
        if (arguments != null) {
            val image = arguments?.getInt("image")
            val caption = arguments?.getString("caption")
            val heading = arguments?.getString("heading")
            val info = arguments?.getString("info")
            sliderItem = SliderItems(image, caption, heading, info,null)
        }
        sliderItem?.image?.let { binding.ivSetItemImage.setImageResource(it) }
        binding.tvInfo.text = sliderItem?.fullInfo
        binding.collapsingToolbar.title = sliderItem?.heading
        binding.bottomSheetBehaviorId.tvQuestion.text =
            "Want to add \"${sliderItem?.heading}\" in your resolution list of comming new year?"

        binding.nsvInfo.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                Log.i(TAG, "Scroll DOWN")
                binding.bottomSheetBehaviorId.llBottomSheet.visibility = View.VISIBLE
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            if (scrollY < oldScrollY) {
                Log.i(TAG, "Scroll UP")
                //binding.bottomSheetBehaviorId.llBottomSheet.visibility = View.GONE
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            if (scrollY == 0) {
                Log.i(TAG, "TOP SCROLL")
                //binding.bottomSheetBehaviorId.llBottomSheet.visibility = View.GONE
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            if (scrollY == v.measuredHeight - v.getChildAt(0).measuredHeight) {
                Log.i(TAG, "BOTTOM SCROLL")
                binding.bottomSheetBehaviorId.llBottomSheet.visibility = View.VISIBLE
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })
    }

    fun playAnimationBlast() {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                binding.laRibbonAnimation.visibility = View.VISIBLE
                binding.laRibbonAnimation.playAnimation()
            }, 100
        )
    }
}