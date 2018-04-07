package com.boukharist.android.androidme.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.android_me.R
import kotlinx.android.synthetic.main.fragment_body_part.*
import java.util.ArrayList

class BodyPartFragment : Fragment() {
    companion object {
        const val TAG = "BodyPartFragment"
        const val IMAGE_IDS_KEY = "BodyPartFragment.IMAGE_IDS_KEY"
        const val INDEX_KEY = "BodyPartFragment.INDEX_KEY"
    }

    var imageIds = listOf<Int>()
    var index = 0

    /*****
     * Life Cycle
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_body_part, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            imageIds = it.getIntegerArrayList(IMAGE_IDS_KEY)
            index = it.getInt(INDEX_KEY)
            Log.i(TAG, "onViewCreated > savedInstanceState > index > $index")
        }

        Log.i(TAG, "onViewCreated > index > $index")
        showImage()
        setImageClickListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(IMAGE_IDS_KEY, imageIds as ArrayList<Int>)
        outState.putInt(INDEX_KEY, index)
        Log.i(TAG, "onSaveInstanceState > index > $index")
    }

    /*****
     * Private Functions
     */
    private fun showImage() {
        if (imageIds.isNotEmpty()) {
            imageIds.let {
                Log.i(TAG, "showImage > index > $index")
                bodyPartImageView.setImageResource(it[index])
            }
        } else {
            throw IllegalStateException("You Must Set ${TAG}#imageIds")
        }
    }

    private fun setImageClickListener() {
        bodyPartImageView.setOnClickListener {
            when {
                (index < imageIds.size - 1) -> index++
                else -> index = 0
            }
            Log.i(TAG, "setImageClickListener > index > $index")
            showImage()
        }
    }
}