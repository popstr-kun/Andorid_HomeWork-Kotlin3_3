package com.example.chapter9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.example.chapter9.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var progressRabbit =0
    private var progressTurtle =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener{
            binding.btnStart.isEnabled = false
            progressRabbit = 0
            progressTurtle = 0
            binding.sbRabbit.progress = 0
            binding.sbTurtle.progress = 0
            runRabbit()
            runTurtle()
        }
    }

     private var handler :Handler = Handler(Looper.myLooper()!!,Handler.Callback {
          when(it.what)
          {
              1->binding.sbRabbit.progress = progressRabbit
              2->binding.sbTurtle.progress = progressTurtle
          }

         if(progressRabbit >= 100 && progressTurtle < 100){
             Toast.makeText(this,"兔子勝利",Toast.LENGTH_LONG).show()
             binding.btnStart.isEnabled = true
         }
         else if(progressTurtle >=100 && progressRabbit < 100){
             Toast.makeText(this,"烏龜勝利", Toast.LENGTH_LONG).show()
             binding.btnStart.isEnabled = true
         }

         return@Callback false
     })

    private fun runRabbit(){
        Thread{
            val sleepProbability = arrayOf(true,true,false)

            while(progressRabbit <= 100 && progressTurtle < 100){
                try {
                    Thread.sleep(100)
                    if (sleepProbability[Random.nextInt(0,2)]) Thread.sleep(300)
                }
                catch(exception:InterruptedException){
                    exception.printStackTrace()
                }
                progressRabbit += 3

                val msg  = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle(){
        Thread{
            while (progressTurtle<=100 && progressRabbit <100){
                try {
                    Thread.sleep(100)
                }
                catch(exception:InterruptedException){
                    exception.printStackTrace()
                }
                progressTurtle += 1

                val msg  = Message()
                msg.what = 2
                handler.sendMessage(msg)
            }
        }.start()
    }
}

