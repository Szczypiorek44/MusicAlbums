package com.example.vamatask.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.example.vamatask.R
import com.example.vamatask.utils.BundleDelegate
import kotlinx.android.synthetic.main.dialog_decision.*

class DecisionDialog : DialogFragment() {

    companion object {
        fun newInstance(
            @StringRes title: Int,
            @StringRes leftButtonText: Int = R.string.no,
            @StringRes rightButtonText: Int = R.string.yes
        ): DecisionDialog {
            return DecisionDialog().apply {
                arguments = Bundle().also {
                    it.title = title
                    it.leftButtonText = leftButtonText
                    it.rightButtonText = rightButtonText
                }
            }
        }
    }

    private var Bundle.title by BundleDelegate.IntDelegate("title")
    private var Bundle.leftButtonText by BundleDelegate.IntDelegate("leftButtonText")
    private var Bundle.rightButtonText by BundleDelegate.IntDelegate("rightButtonText")

    private var onLeftButtonClick: (() -> Unit)? = null
    private var onRightButtonClick: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dialog_decision, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        titleTextView.text = getString(requireArguments().title)
        leftButton.text = getString(requireArguments().leftButtonText)
        rightButton.text = getString(requireArguments().rightButtonText)

        leftButton.setOnClickListener { onLeftButtonClick?.invoke() }
        rightButton.setOnClickListener { onRightButtonClick?.invoke() }
    }

    fun setOnLeftButtonClick(onLeftButtonClick: () -> Unit) {
        this.onLeftButtonClick = onLeftButtonClick
    }

    fun setOnRightButtonClick(onRightButtonClick: () -> Unit) {
        this.onRightButtonClick = onRightButtonClick
    }
}