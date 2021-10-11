package com.example.geneticalgoritm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.view.Graph2D

class GraphFragment(
    var vertex: Int = 20,
    var edges: ArrayList<Triple<Int, Int, Double>> = arrayListOf()
) : Fragment() {
    private lateinit var field: Graph2D

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return Graph2D(context, vertex, edges)
    }

//    fun setNewVertex(newCount: Int){
//        vertex = newCount
//        field.nodes = vertex
//        field.invalidate()
//    }
//
//    fun setNewEdges(newEdges: ArrayList<Triple<Int, Int, Double>>) {
//        edges = newEdges
//        field.edges = edges
//        field.invalidate()
//    }
//
//    fun refresh(){
//        field.let{
//            it.nodes = vertex
//            it.edges = edges
//            it.invalidate()
//        }
//    }


}