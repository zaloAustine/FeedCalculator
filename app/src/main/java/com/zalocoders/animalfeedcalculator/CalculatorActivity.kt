package com.zalocoders.animalfeedcalculator

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


class CalculatorActivity : AppCompatActivity() {
    var feedList: MutableList<Feed>? = null
    var feedSelected: MutableList<Feed>? = null
    var kgs: Int = 0
    var stringBuilder: StringBuilder? = null
    lateinit var button: Button
    lateinit var recyclerView: RecyclerView
    lateinit var items: TextView
    lateinit var pageDesc: TextView
    lateinit var container: IntArray
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        kgs = intent.getStringExtra("KGS")?.toInt()!!
        stringBuilder = StringBuilder()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while we balance your feeds....")

        items = findViewById(R.id.items)
        pageDesc = findViewById(R.id.pageDesc)

        button = findViewById(R.id.button)
        button.setOnClickListener {

            feedSelected?.size?.let { it1 -> divider(kgs, it1) }

            calculate()
        }

        feedList = mutableListOf()
        feedSelected = ArrayList()

        addItemToFeedList()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val feedAdapter = feedList?.let {
            FeedAdapter(it, this) { it, check ->
                if (!check && feedSelected?.contains(it) == true) {
                    feedSelected?.remove(it)
                } else {
                    feedSelected?.add(it)
                }
                stringBuilder?.clear()

                feedSelected?.forEach { feed ->
                    stringBuilder?.append("${feed.name} Protein ${feed.CP} Minerals${feed.MN} ${feed.cost}ksh Per (kg)\n")
                }

                items.text = stringBuilder.toString()
            }
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = feedAdapter
        }
    }

    private fun addItemToFeedList() {

        val feed5 = Feed(name = "Maize meal", CP = 9.70, MN = 13.60, cost = 35, id = 2)
        val feed6 = Feed(name = "Soyabeans meal", CP = 45.10, MN = 12.10, cost = 40, id = 2)
        val feed7 = Feed(name = "Cotton seed cake ", CP = 29.80, MN = 10.30, cost = 30, id = 1)
        val feed8 = Feed(name = "Calliandra", CP = 12.50, MN = 5.24, cost = 11, id = 2)
        val feed9 = Feed(name = "Laucaena", CP = 21.80, MN = 8.32, cost = 11, id = 3)
        val feed10 = Feed(name = "Acacia", CP = 19.38, MN = 5.46, cost = 11, id = 2)
        val feed11 = Feed(name = "Star grass hay", CP = 8.00, MN = 7.50, cost = 20, id = 1)
        val feed12 = Feed(name = "Napier grass hay", CP = 10.30, MN = 8.00, cost = 20, id = 1)
        val feed13 = Feed(name = "Sorghum", CP = 11.80, MN = 12.60, cost = 32, id = 1)
        val feed14 = Feed(name = "Maize bran", CP = 8.20, MN = 11.50, cost = 17, id = 1)
        val feed15 = Feed(name = "Vitamin mineral premix", CP = 0.00, MN = 0.00, cost = 50, id = 2)

        feedList?.add(feed5)
        feedList?.add(feed6)
        feedList?.add(feed7)
        feedList?.add(feed8)
        feedList?.add(feed9)
        feedList?.add(feed10)
        feedList?.add(feed11)
        feedList?.add(feed12)
        feedList?.add(feed13)
        feedList?.add(feed14)
        feedList?.add(feed15)
    }

    private fun calculate() {

        progressDialog.show()
        Handler().postDelayed(Runnable {
            progressDialog.dismiss()
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()

        }, 3000)

        val linearLayout = findViewById<LinearLayout>(R.id.mainLayout)
        linearLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        button.visibility = View.GONE
        items.visibility = View.GONE
        pageDesc.text =
            "Mix the following feeds in the given ratios  to achieve $kgs kg(s) Balanced Mixture"
        linearLayout.addView(createViews())
    }

    private fun createViews(): LinearLayout {

        val ll = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        var count = 0
        feedSelected?.forEach { feed ->

            val inflater =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView: View = inflater.inflate(R.layout.result_view, null, false)
            rowView.id = count


            val kgs = rowView.findViewById<TextView>(R.id.kgs)
            val name = rowView.findViewById<TextView>(R.id.name)

            kgs.text = "${container[count]} kgs "

            name.text = "${feed.name}"

            ll.addView(rowView)

            count++
        }
        return ll
    }

    fun divider(number: Int, divisions: Int) {
        var number = number
        val rand = Random()
        container = IntArray(divisions)
        print("$number->")
        while (number > 0) {
            container[rand.nextInt(divisions)]++
            number--
        }
    }
}