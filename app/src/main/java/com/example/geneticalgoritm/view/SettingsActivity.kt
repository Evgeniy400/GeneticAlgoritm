package com.example.geneticalgoritm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.geneticalgoritm.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        findViewById<EditText>(R.id.editTextNumber2).setText(intent.getStringExtra("chromosomeLen"))
        findViewById<EditText>(R.id.editTextNumber3).setText(intent.getStringExtra("populationCount"))
        findViewById<EditText>(R.id.editTextNumber4).setText(intent.getStringExtra("generationCount"))
        findViewById<EditText>(R.id.editTextNumberDecimal).setText(intent.getStringExtra("crossoverChance"))
        findViewById<EditText>(R.id.editTextNumberDecimal2).setText(intent.getStringExtra("mutationChance"))

        findViewById<Button>(R.id.button4).setOnClickListener{
            var data = Intent()
            data.putExtra("populationCount", findViewById<EditText>(R.id.editTextNumber3).text.toString())
            data.putExtra("chromosomeLen", findViewById<EditText>(R.id.editTextNumber2).text.toString())
            data.putExtra("generationCount", findViewById<EditText>(R.id.editTextNumber4).text.toString())
            data.putExtra("crossoverChance", findViewById<EditText>(R.id.editTextNumberDecimal).text.toString())
            data.putExtra("mutationChance", findViewById<EditText>(R.id.editTextNumberDecimal2).text.toString())
            setResult(RESULT_OK, data)
            finish()
        }

    }
}