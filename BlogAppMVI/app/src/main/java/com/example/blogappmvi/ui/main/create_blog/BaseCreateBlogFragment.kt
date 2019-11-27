package com.example.blogappmvi.ui.main.create_blog

import android.content.Context
import android.util.Log
import com.example.blogappmvi.ui.DataStateChangedListener
import dagger.android.support.DaggerFragment

abstract class BaseCreateBlogFragment : DaggerFragment(){

    val TAG: String = "AppDebug"

    lateinit var stateChangeListener: DataStateChangedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            stateChangeListener = context as DataStateChangedListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement DataStateChangeListener" )
        }
    }
}