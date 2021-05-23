package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}

        }

    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        //We are using toString() func because the text is of Editable Type and not String
        val cost = stringInTextField.toDoubleOrNull() // Converted the string into decimal
        if (cost == null) {
            binding.tipResult.text = " "
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var resultTip = cost * tipPercentage
        val roundUp = binding.roundUpOption.isChecked
        if (roundUp) {
            resultTip = kotlin.math.ceil(resultTip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(resultTip)
        //getCurrencyInstance reads the System State of device and gets to know the language and preference of user
        //System then formats the currency automatically based on that
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
               //id of textView in XML code          string resource in strings.xml

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}