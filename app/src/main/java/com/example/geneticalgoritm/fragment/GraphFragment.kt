package com.example.geneticalgoritm.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.view.Graph2D

class GraphFragment(var vertex: Int = 25, var edges: ArrayList<Triple<Int, Int, Double>> = arrayListOf()) : Fragment() {
    private lateinit var field: Graph2D

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return Graph2D(context, vertex, edges)

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        field = view.findViewById(R.id.fragmentContainerView)
//    }
}