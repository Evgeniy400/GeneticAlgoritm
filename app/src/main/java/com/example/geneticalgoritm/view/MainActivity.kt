package com.example.geneticalgoritm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import com.example.geneticalgoritm.R
import com.example.geneticalgoritm.fragment.GraphFragment
import com.example.geneticalgoritm.presenter.MainPresenter

class MainActivity : AppCompatActivity() {
    private var presenter = MainPresenter(this)
//    private val graph = GraphFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setContentView(graph2D)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, GraphFragment()).commit()
        findViewById<AppCompatImageView>(R.id.imageButton).setOnClickListener {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragmentContainerView2, GraphFragment(++click)).addToBackStack(null)
//                .commit()
            var data = Intent(this, SettingsActivity::class.java)
            data.putExtra("populationCount", presenter.model.populationCount.toString())
            data.putExtra("chromosomeLen", presenter.model.chromosomeLen.toString())
            data.putExtra("generationCount", presenter.model.generationCount.toString())
            data.putExtra("crossoverChance", presenter.model.crossoverChance.toString())
            data.putExtra("mutationChance", presenter.model.mutationChance.toString())
            startActivityForResult(data, 1)
        }
        findViewById<AppCompatImageView>(R.id.imageButton2).setOnClickListener {
            presenter.solve()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        presenter.getNewParameters(
            data.getStringExtra("populationCount")!!.toInt(),
            data.getStringExtra("chromosomeLen")!!.toInt(),
            data.getStringExtra("generationCount")!!.toInt(),
            data.getStringExtra("crossoverChance")!!.toDouble(),
            data.getStringExtra("mutationChance")!!.toDouble()
        )

//        graph.vertex = data.getStringExtra("chromosomeLen")!!.toInt()
//        graph.refresh()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, GraphFragment( data.getStringExtra("chromosomeLen")!!.toInt())).commit()
    }


    public fun drawGraph(vertex: Int, edges: ArrayList<Triple<Int, Int, Double>>) {
//        graph.vertex = vertex
//        graph.edges = ArrayList(edges)
//        graph.refresh()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView2, GraphFragment(vertex, edges)).addToBackStack(null)
            .commit()

    }

}