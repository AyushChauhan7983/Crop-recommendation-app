package com.example.crop_recommendation_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class PredictFragment : Fragment() {
    val url = "https://crop-recommendation-android-app.onrender.com/predict"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_predict, container, false)
        val nitrogen = view.findViewById<EditText>(R.id.nitrogen)
        val phosphorous = view.findViewById<EditText>(R.id.phosphorous)
        val potassium = view.findViewById<EditText>(R.id.potassium)
        val temperature = view.findViewById<EditText>(R.id.temperature)
        val humidity = view.findViewById<EditText>(R.id.humidity)
        val pH = view.findViewById<EditText>(R.id.ph)
        val rainfall = view.findViewById<EditText>(R.id.rainfall)
        val predict = view.findViewById<Button>(R.id.predict)

        predict.setOnClickListener {
            if (areAllFieldsFilled(nitrogen, phosphorous, potassium, temperature, humidity, pH, rainfall)) {
                if (areValuesWithinLimits(nitrogen, phosphorous, potassium, temperature, humidity, pH, rainfall)) {
                    makePredictionRequest(nitrogen, phosphorous, potassium, temperature, humidity, pH, rainfall)
                } else {
                    Toast.makeText(requireContext(), "Input values exceed the specified limits", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun areAllFieldsFilled(vararg fields: EditText): Boolean {
        for (field in fields) {
            if (field.text.toString().isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun areValuesWithinLimits(
        nitrogen: EditText, phosphorous: EditText, potassium: EditText,
        temperature: EditText, humidity: EditText, pH: EditText, rainfall: EditText
    ): Boolean {
        val maxLimits = mapOf(
            "nitrogen" to 120.0,
            "phosphorous" to 80.0,
            "potassium" to 50.0,
            "temperature" to 50.0,
            "humidity" to 100.0,
            "pH" to 8.0,
            "rainfall" to 300.0
        )

        val inputValues = mapOf(
            "nitrogen" to nitrogen.text.toString().toDoubleOrNull(),
            "phosphorous" to phosphorous.text.toString().toDoubleOrNull(),
            "potassium" to potassium.text.toString().toDoubleOrNull(),
            "temperature" to temperature.text.toString().toDoubleOrNull(),
            "humidity" to humidity.text.toString().toDoubleOrNull(),
            "pH" to pH.text.toString().toDoubleOrNull(),
            "rainfall" to rainfall.text.toString().toDoubleOrNull()
        )

        for ((fieldName, value) in inputValues) {
            if (value == null || value > (maxLimits[fieldName] ?: Double.MAX_VALUE)) {
                return false
            }
        }
        return true
    }

    private fun makePredictionRequest(
        nitrogen: EditText, phosphorous: EditText, potassium: EditText,
        temperature: EditText, humidity: EditText, pH: EditText, rainfall: EditText
    ) {
        // Validate input values against limits
        if (areValuesWithinLimits(nitrogen, phosphorous, potassium, temperature, humidity, pH, rainfall)) {
            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val data = jsonObject.getString("crop")
                        val mssg = data.toString()
                        activity?.let { it1 -> Snackbar.make(it1.findViewById(R.id.predictscreen), "Best crop to be cultivated in this type of conditions is: " + mssg, 10000).show() }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = mutableMapOf()
                    params["phosphorous"] = phosphorous.text.toString()
                    params["nitrogen"] = nitrogen.text.toString()
                    params["potassium"] = potassium.text.toString()
                    params["temperature"] = temperature.text.toString()
                    params["humidity"] = humidity.text.toString()
                    params["pH"] = pH.text.toString()
                    params["rainfall"] = rainfall.text.toString()

                    return params
                }
            }
            val queue = Volley.newRequestQueue(requireContext())
            queue.add(stringRequest)
        } else {
            Toast.makeText(requireContext(), "Input values exceed the specified limits", Toast.LENGTH_SHORT).show()
        }
    }
}



