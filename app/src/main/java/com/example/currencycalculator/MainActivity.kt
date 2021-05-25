package com.example.currencycalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class MainActivity : AppCompatActivity() {

    lateinit var inputSpinner : Spinner
    lateinit var outputSpinner : Spinner
    lateinit var button_calculate : Button
    lateinit var editTextNumber_input : EditText
    lateinit var textView_result : TextView
    lateinit var countriesList : Array<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //variables
        var inputSpinnerText = ""
        var outputSpinnerText = ""
        inputSpinner = findViewById<Spinner>(R.id.inputSpinner)
        outputSpinner = findViewById<Spinner>(R.id.outputSpinner)
        button_calculate = findViewById(R.id.button_calculate)
        editTextNumber_input = findViewById<EditText>(R.id.editTextNumber_input)
        textView_result = findViewById(R.id.textView_result)
        countriesList = arrayOf("AUD", "CAD", "EUR", "USD", "JPY", "DKK", "GBP", "HUF", "PLN", "KRW")



        //gson
        val inputStream = resources.openRawResource(R.raw.currency)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        val gson = Gson()
        val jsonDataType = object : TypeToken<List<Currencies>>() {}.type
        val currencyList = gson.fromJson<List<Currencies>>(jsonString, jsonDataType)


        //button_calculate
        button_calculate.setOnClickListener {
            inputSpinnerText = inputSpinner.selectedItem.toString()
            outputSpinnerText = outputSpinner.selectedItem.toString()
            var inputCurrencyRatio = 0.0
            var outputCurrencyRatio = 0.0
            var index = 0
            var index1 = 0
            var inputValue = editTextNumber_input.text.toString().toDouble()
            var resultValue = 0.0

            //Finds the currency ratio for selected inputSpinner
            while(index < currencyList.size) {
                if(currencyList[index].country == inputSpinnerText) {
                    inputCurrencyRatio = currencyList[index].currency
                }
                index++
            }

            //Finds the currency ratio for selected outputSpinner
            while(index1 < currencyList.size) {
                if(currencyList[index1].country == outputSpinnerText) {
                    outputCurrencyRatio = currencyList[index1].currency
                }
                index1++
            }

            // result = (inputValue) * (Currency 2 Ratio) / (Currency 1 Ratio)
                resultValue = (inputValue) * outputCurrencyRatio / inputCurrencyRatio
                textView_result.text = resultValue.toString() + " " + outputSpinnerText


        }

            // call functions
            inputSpinnerFunction()
            outputSpinnerFunction()


    }


    private fun inputSpinnerFunction() {
        val inputAdapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, countriesList)
        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputSpinner.adapter = inputAdapter

    }

    private fun outputSpinnerFunction() {
        val outputAdapter = ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, countriesList)
        outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        outputSpinner.adapter = outputAdapter

    }



}


