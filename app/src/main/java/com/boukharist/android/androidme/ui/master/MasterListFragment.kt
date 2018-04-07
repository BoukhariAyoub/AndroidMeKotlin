package com.boukharist.android.androidme.ui.master

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.boukharist.android.androidme.data.AndroidImageAssets
import com.example.android.android_me.R
import kotlinx.android.synthetic.main.fragment_master_list.*

class MasterListFragment : Fragment() {

    companion object {
        const val TAG = "MasterListFragment"
    }

    interface OnImageClickListener {
        fun onImageSelected(position: Int)
    }

    private lateinit var callback: OnImageClickListener


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = context as OnImageClickListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "Context must implements OnImageClickListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_master_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridView.adapter = MasterListAdapter(context, AndroidImageAssets.getAll())
        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            callback.onImageSelected(position)
        }
    }
}