package com.example.photoeditorapp.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorapp.R
import com.example.photoeditorapp.adapter.ColorPickerAdapter
import com.example.photoeditorapp.databinding.FragmentBottomPropertiesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PropertiesBSFragment : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener
{
    private var mProperties: Properties? = null
    private lateinit var mBinding : FragmentBottomPropertiesDialogBinding

    interface Properties {
        fun onColorChanged(colorCode: Int)
        fun onOpacityChanged(opacity: Int)
        fun onShapeSizeChanged(shapeSize: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBottomPropertiesDialogBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.sbOpacity.setOnSeekBarChangeListener(this)
        mBinding.sbSize.setOnSeekBarChangeListener(this)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvColors.layoutManager = layoutManager
        mBinding.rvColors.setHasFixedSize(true)
        val colorPickerAdapter = activity?.let { ColorPickerAdapter(it) }
        colorPickerAdapter?.setOnColorPickerClickListener(object :
            ColorPickerAdapter.OnColorPickerClickListener {
            override fun onColorPickerClickListener(colorCode: Int) {
                if (mProperties != null) {
                    dismiss()
                    mProperties?.onColorChanged(colorCode)
                }
            }
        })
        mBinding.rvColors.adapter = colorPickerAdapter

    }

    fun setPropertiesChangeListener(properties: Properties?) {
        mProperties = properties
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when(seekBar.id){
            R.id.sbOpacity -> if (mProperties != null){
                mProperties?.onOpacityChanged(progress)
            }
            R.id.sbSize -> if (mProperties != null){
                mProperties?.onShapeSizeChanged((progress))
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}

}