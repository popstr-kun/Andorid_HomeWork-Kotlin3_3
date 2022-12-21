package com.example.chapter9_2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chapter9_2.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener{
            when{
                binding.edHeight.length() < 1 -> Toast.makeText(this,"請輸入身高",Toast.LENGTH_SHORT).show()
                binding.edWeight.length() < 1 -> Toast.makeText(this,"請輸入體重",Toast.LENGTH_SHORT).show()
                binding.edAge.length()    < 1 -> Toast.makeText(this,"請輸入年齡",Toast.LENGTH_SHORT).show()
                else -> runCoroutines()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    private fun runCoroutines(){
        binding.tvWeight.text = "標準體重\n 無"
        binding.tvFat.text    = "體脂肪\n 無"
        binding.tvBmi.text    = "BMI\n 無"

        binding.progressBar2.progress = 0
        binding.tvProgress.text = "0%"

        binding.llProgress.visibility = View.VISIBLE
        GlobalScope.launch (Dispatchers.Main){
            var progress = 0

            while(progress < 100){
                delay(50)

                binding.progressBar2.progress = progress
                binding.tvProgress.text = "$progress%"

                progress ++
            }

            binding.llProgress.visibility = View.GONE

            val height = binding.edHeight.text.toString().toDouble()
            val weight = binding.edWeight.text.toString().toDouble()
            val age    = binding.edAge.text.toString().toDouble()
            val bmi    = weight/((height/100).pow(2))

            val (stand_weight, body_fat) = if (binding.btnBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }

            binding.tvWeight.text = "標準體重 \n${String.format("%.2f", stand_weight)}"
            binding.tvFat.text    = "體脂肪 \n${String.format("%.2f", body_fat)}"
            binding.tvBmi.text    = "BMI \n${String.format("%.2f", bmi)}"
        }
    }
}