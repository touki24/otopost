package com.touki.otopost.common.extension

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.touki.otopost.common.R

/** Created by mentok on 9/13/21 */
fun Fragment.showMessage(stringId: Int, snackbarDuration: Int = Snackbar.LENGTH_LONG, view: View? = null) {
    activity?.let {
        val msg = getString(stringId)
        view?.let { view ->
            Snackbar.make(view, msg, snackbarDuration).apply {
                view.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_rectangle_rounded_purple)
                show()
            }
        } ?: run {
            Snackbar.make(it.findViewById(android.R.id.content), msg, snackbarDuration).apply {
                view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_rectangle_rounded_purple)
                show()
            }
        }
    }
}

fun Fragment.showMessage(message: String?, snackbarDuration: Int = Snackbar.LENGTH_LONG, view: View? = null) {
    activity?.let {
        val msg = if(message.isNullOrEmpty()) { "" } else { message }
        view?.let { view ->
            Snackbar.make(view, msg, snackbarDuration).apply {
                view.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_rectangle_rounded_purple)
                show()
            }
        } ?: run {
            Snackbar.make(it.findViewById(android.R.id.content), msg, snackbarDuration).apply {
                view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_rectangle_rounded_purple)
                show()
            }
        }
    }
}

fun Activity.showMessage(message: String?, snackbarDuration: Int = Snackbar.LENGTH_LONG, view: View? = null) {
    val msg = if(message.isNullOrEmpty()) { "" } else { message }
    view?.let { _view ->
        Snackbar.make(_view, msg, snackbarDuration).apply {
            _view.background = ContextCompat.getDrawable(context, R.drawable.shape_rectangle_rounded_purple)
            show()
        }
    } ?: run {
        Snackbar.make(this.findViewById(android.R.id.content), msg, snackbarDuration).apply {
            view?.background = ContextCompat.getDrawable(context, R.drawable.shape_rectangle_rounded_purple)
            show()
        }
    }
}