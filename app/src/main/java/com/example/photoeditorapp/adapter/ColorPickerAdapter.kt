package com.example.photoeditorapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorapp.R
import com.example.photoeditorapp.databinding.ColorPickerItemListBinding
import java.util.ArrayList

class ColorPickerAdapter internal constructor(
    private var context: Context,
    colorPickerColors : List<Int>
) : RecyclerView.Adapter<ColorPickerAdapter.ViewHolder>(){
    private var inflater: LayoutInflater
    private val colorPickerColors: List<Int>
    private lateinit var onColorPickerClickListener: OnColorPickerClickListener

    interface OnColorPickerClickListener {
        fun onColorPickerClickListener(colorCode: Int)
    }

    //Initializing the view Holder class
    inner class ViewHolder(val binding : ColorPickerItemListBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.colorPickerView.setOnClickListener {
                onColorPickerClickListener.onColorPickerClickListener(
                    colorPickerColors[adapterPosition]
                )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ColorPickerItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return colorPickerColors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = colorPickerColors[position]
        holder.binding.colorPickerView.setBackgroundColor(item)
    }

    //passing context
    internal constructor(context: Context) : this(context, getDefaultColors(context)) {
        this.context = context
        inflater = LayoutInflater.from(context)
    }

    fun setOnColorPickerClickListener(onColorPickerClickListener: OnColorPickerClickListener) {
        this.onColorPickerClickListener = onColorPickerClickListener
    }

    companion object {

        //Providing option to select form the defined color pallets
        fun getDefaultColors(context: Context): List<Int> {
            val colorPickerColors = ArrayList<Int>()
            colorPickerColors.add(ContextCompat.getColor((context), R.color.blue_color_picker))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.brown_color_picker))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.green_color_picker))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.orange_color_picker))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.red_color_picker))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.black))
            colorPickerColors.add(
                ContextCompat.getColor(
                    (context),
                    R.color.red_orange_color_picker
                )
            )
            colorPickerColors.add(
                ContextCompat.getColor(
                    (context),
                    R.color.sky_blue_color_picker
                )
            )
            colorPickerColors.add(ContextCompat.getColor((context), R.color.violet_color_picker))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.white))
            colorPickerColors.add(ContextCompat.getColor((context), R.color.yellow_color_picker))
            colorPickerColors.add(
                ContextCompat.getColor(
                    (context),
                    R.color.yellow_green_color_picker
                )
            )
            return colorPickerColors
        }
    }

    init {
        inflater = LayoutInflater.from(context)
        this.colorPickerColors = colorPickerColors
    }

}