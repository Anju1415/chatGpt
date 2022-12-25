package com.example.investinyourself

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.investinyourself.databinding.SlideItemContainerBinding


class SliderAdapter(
    private val sliderItems: ArrayList<SliderItems>,
    private val viewPager2: ViewPager2,
    val clickListener: (item:SliderItems)->Unit
) : RecyclerView.Adapter<SliderAdapter.ContainerViewHolder>() {

    inner class ContainerViewHolder(val binding: SlideItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapter.ContainerViewHolder {
        val binding =
            SlideItemContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ContainerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderAdapter.ContainerViewHolder, position: Int) {
        val item = sliderItems[position]
        item.image?.let { holder.binding.imageSlide.setImageResource(it) }
        holder.binding.tvCaption.text = item.caption
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
        holder.binding.imageSlide.setOnClickListener {
            clickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

}

