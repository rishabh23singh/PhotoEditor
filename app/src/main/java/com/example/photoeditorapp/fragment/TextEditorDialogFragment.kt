package com.example.photoeditorapp.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorapp.R
import com.example.photoeditorapp.adapter.ColorPickerAdapter
import com.example.photoeditorapp.databinding.AddTextDialogBinding

class TextEditorDialogFragment : DialogFragment() {

    private lateinit var mBinding : AddTextDialogBinding
    private lateinit var mInputMethodManager: InputMethodManager
    private var mColorCode = 0
    private var mTextEditorListener: TextEditorListener? = null

    interface TextEditorListener {
        fun onDone(inputText: String, colorCode: Int)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        //Make dialog full screen with transparent background
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    //Binding View with binding generated class
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = AddTextDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        mInputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mBinding.addTextColorPickerRecyclerView.layoutManager = layoutManager
        mBinding.addTextColorPickerRecyclerView.setHasFixedSize(true)
        val colorPickerAdapter = ColorPickerAdapter(activity)

        //This listener will change the text color when clicked on any color from picker
        colorPickerAdapter.setOnColorPickerClickListener(object :
            ColorPickerAdapter.OnColorPickerClickListener {
            override fun onColorPickerClickListener(colorCode: Int) {
                mColorCode = colorCode
                mBinding.addTextEditText.setTextColor(colorCode)
            }
        })

        mBinding.addTextColorPickerRecyclerView.adapter = colorPickerAdapter
        val arguments = requireArguments()

        mBinding.addTextEditText.setText(arguments.getString(EXTRA_INPUT_TEXT))
        mColorCode = arguments.getInt(EXTRA_COLOR_CODE)
        mBinding.addTextEditText.setTextColor(mColorCode)
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        //Make a callback on activity when user is done with text editing
        mBinding.addTextDoneTv.setOnClickListener { onClickListenerView ->
            mInputMethodManager.hideSoftInputFromWindow(onClickListenerView.windowToken, 0)
            dismiss()
            val inputText = mBinding.addTextEditText.text.toString()
            val textEditorListener = mTextEditorListener
            if (inputText.isNotEmpty() && textEditorListener != null) {
                textEditorListener.onDone(inputText, mColorCode)
            }
        }

    }

    //Callback to listener if user is done with text editing
    fun setOnTextEditorListener(textEditorListener: TextEditorListener) {
        mTextEditorListener = textEditorListener
    }

    companion object {
        private val TAG: String = TextEditorDialogFragment::class.java.simpleName
        const val EXTRA_INPUT_TEXT = "extra_input_text"
        const val EXTRA_COLOR_CODE = "extra_color_code"

        //Show dialog with provide text and text color
        //Show dialog with default text input as empty and text color white
        @JvmOverloads
        fun show(
            appCompatActivity: AppCompatActivity,
            inputText: String = "",
            @ColorInt colorCode: Int = ContextCompat.getColor(appCompatActivity, R.color.white)
        ): TextEditorDialogFragment {
            val args = Bundle()
            args.putString(EXTRA_INPUT_TEXT, inputText)
            args.putInt(EXTRA_COLOR_CODE, colorCode)
            val fragment = TextEditorDialogFragment()
            fragment.arguments = args
            fragment.show(appCompatActivity.supportFragmentManager, TAG)
            return fragment
        }
    }
}