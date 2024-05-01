package com.example.jutak1.usertracksapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jutak1.usertracksapp.databinding.ActivityStepCountBinding

class StepCountActivity : AppCompatActivity(), SensorEventListener {
    private  lateinit var binding: ActivityStepCountBinding
    private  var sensorManager: SensorManager?= null
    private var runing = false
    private var totalSteps = 0f
    private var previousoTotalSteps = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStepCountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
//        resetSteps()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        runing = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor ==null){
            Toast.makeText(this, "Aucun capteurs detectes sur cette appareil", Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if(runing){
            totalSteps = event!!.values[0]
            val currentSteps  = totalSteps.toInt() - previousoTotalSteps.toInt()
            binding.tvStepsTaken.text = ("$currentSteps")

            binding.progressCircular.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    private fun resetSteps(){
        binding.tvStepsTaken.setOnClickListener{
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }
        binding.tvStepsTaken.setOnLongClickListener{
            previousoTotalSteps = totalSteps
            binding.tvStepsTaken.text = 0.toString()
            saveData()
            true
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousoTotalSteps)
        editor.apply()
    }
    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        Log.d("mainActivity", "$savedNumber")
        previousoTotalSteps = savedNumber
    }
}