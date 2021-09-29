package com.example.geneticalgoritm.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.view.Graph2D

class GraphFragment : Fragment() {
    private lateinit var field: Graph2D

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        return inflater.inflate(R.layout.fragment_graph, container, false)
        var tmp = ArrayList<Triple<Int, Int, Double>>()
        for (i in 0..4)
            for(j in i+1..5)
                tmp.add(Triple(i, j, 0.0))

        return Graph2D(context,6, tmp)

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        field = view.findViewById(R.id.fragmentContainerView)
//    }
}