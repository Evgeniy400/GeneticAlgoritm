package com.example.geneticalgoritm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.fragment.GraphFragment

class MainActivity : AppCompatActivity() {
    private var graph = GraphFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var graph2D = Graph2D(this)
//        setContentView(graph2D)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView2, GraphFragment()).commit()
    }

}