package com.example.photoeditorapp.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorapp.R
import com.example.photoeditorapp.adapter.ColorPickerAdapter
import com.example.photoeditorapp.databinding.FragmentBottomShapesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ja.burhanrashid52.photoeditor.shape.ShapeType

class ShapeBSFragment  : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener
{
    private lateinit var mBinding : FragmentBottomShapesDialogBinding
    private var mProperties: Properties? = null

    interface Properties {
        fun onColorChanged(colorCode: Int)
        fun onOpacityChanged(opacity: Int)
        fun onShapeSizeChanged(shapeSize: Int)
        fun onShapePicked(shapeType: ShapeType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBottomShapesDialogBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.shapeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.lineRadioButton -> {
                    mProperties!!.onShapePicked(ShapeType.Line)
                }
                R.id.arrowRadioButton -> {
                    mProperties!!.onShapePicked(ShapeType.Arrow())
                }
                R.id.ovalRadioButton -> {
                    mProperties!!.onShapePicked(ShapeType.Oval)
                }
                R.id.rectRadioButton -> {
                    mProperties!!.onShapePicked(ShapeType.Rectangle)
                }
                else -> {
                    mProperties!!.onShapePicked(ShapeType.Brush)
                }
            }
        }

        mBinding.shapeOpacity.setOnSeekBarChangeListener(this)
        mBinding.shapeSize.setOnSeekBarChangeListener(this)

        val activity = requireActivity()
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mBinding.shapeColors.layoutManager = layoutManager
        mBinding.shapeColors.setHasFixedSize(true)
        val colorPickerAdapter = ColorPickerAdapter(activity)
        colorPickerAdapter.setOnColorPickerClickListener(object :
            ColorPickerAdapter.OnColorPickerClickListener {
            override fun onColorPickerClickListener(colorCode: Int) {
                if (mProperties != null) {
                    dismiss()
                    mProperties!!.onColorChanged(colorCode)
                }
            }
        })
        mBinding.shapeColors.adapter = colorPickerAdapter
    }

    fun setPropertiesChangeListener(properties: Properties?) {
        mProperties = properties
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.shapeOpacity -> if (mProperties != null) {
                mProperties!!.onOpacityChanged(progress)
            }
            R.id.shapeSize -> if (mProperties != null) {
                mProperties!!.onShapeSizeChanged(progress)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}