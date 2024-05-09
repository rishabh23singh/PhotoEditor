package com.example.photoeditorapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorapp.R
import com.example.photoeditorapp.databinding.RowEditingToolsBinding
import com.example.photoeditorapp.tools.ToolType
import java.util.ArrayList

class EditingToolsAdapter(private val mOnItemSelected: OnItemSelected) :
    RecyclerView.Adapter<EditingToolsAdapter.ViewHolder>() {
    private val mToolList: MutableList<ToolModel> = ArrayList()

    interface OnItemSelected {
        fun onToolSelected(toolType: ToolType)
    }

    internal inner class ToolModel(
        val mToolName: String,
        val mToolIcon: Int,
        val mToolType: ToolType
    )


    inner class ViewHolder(val binding : RowEditingToolsBinding) : RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.rootEditTool.setOnClickListener {
                mOnItemSelected.onToolSelected(
                    mToolList[layoutPosition].mToolType
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowEditingToolsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return mToolList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mToolList[position]
        holder.binding.txtTool.text = item.mToolName
        holder.binding.imgToolIcon.setImageResource(item.mToolIcon)
    }

    init {
        mToolList.add(ToolModel("Shape", R.drawable.ic_oval, ToolType.SHAPE))
        mToolList.add(ToolModel("Text", R.drawable.ic_text, ToolType.TEXT))
        mToolList.add(ToolModel("Eraser", R.drawable.ic_eraser, ToolType.ERASER))
        mToolList.add(ToolModel("Filter", R.drawable.ic_photo_filter, ToolType.FILTER))
        mToolList.add(ToolModel("Emoji", R.drawable.ic_insert_emoticon, ToolType.EMOJI))
        mToolList.add(ToolModel("Sticker", R.drawable.ic_sticker, ToolType.STICKER))
    }

}