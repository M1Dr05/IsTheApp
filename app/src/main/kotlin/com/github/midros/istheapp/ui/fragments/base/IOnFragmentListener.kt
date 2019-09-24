package com.github.midros.istheapp.ui.fragments.base

interface IOnFragmentListener {
    fun onBackPressed(): Boolean
    fun onHideFragment()
}