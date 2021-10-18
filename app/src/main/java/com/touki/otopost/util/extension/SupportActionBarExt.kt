package com.touki.otopost.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.setSupportActionBar(toolbar: Toolbar, withBackButton: Boolean = true) : Toolbar {
    (activity as AppCompatActivity).apply {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }
    return toolbar
}

fun Fragment.setSupportActionBar(enableBackButton: Boolean = false) {
    (activity as AppCompatActivity).apply {
        supportActionBar?.setDisplayHomeAsUpEnabled(enableBackButton)

    }
}