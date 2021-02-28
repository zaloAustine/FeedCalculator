package com.zalocoders.animalfeedcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val next = findViewById<Button>(R.id.next)
        val kgs = findViewById<EditText>(R.id.kgs)

        next.setOnClickListener {
            if (kgs.text.toString() != "") {
                val intent = Intent(this, CalculatorActivity::class.java)
                intent.putExtra("KGS", kgs.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "please Enter KG(s)", Toast.LENGTH_LONG).show()
            }
        }
    }
}