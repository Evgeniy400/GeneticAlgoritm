package com.example.geneticalgoritm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.fragment.GraphFragment
import com.example.geneticalgoritm.presenter.MainPresenter

class MainActivity : AppCompatActivity() {
    private var graph = GraphFragment()
    private var click = 20;
    private var presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var graph2D = Graph2D(this)
//        setContentView(graph2D)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, GraphFragment(click)).commit()
        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragmentContainerView2, GraphFragment(++click)).addToBackStack(null)
//                .commit()
            presenter.solve()
        }
    }

    public fun drawGraph(vertex: Int, edges: ArrayList<Triple<Int, Int, Double>>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, GraphFragment(vertex, edges)).addToBackStack(null)
            .commit()

    }

}