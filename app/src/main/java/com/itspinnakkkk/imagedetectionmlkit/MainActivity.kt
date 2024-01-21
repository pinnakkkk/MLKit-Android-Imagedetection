package com.itspinnakkkk.imagedetectionmlkit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.itspinnakkkk.imagedetectionmlkit.ui.theme.ImageDetectionMLkitTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img: ImageView = findViewById(R.id.imageToLabel)
// assets folder image file name with extension
        val fileName = "flower1.jpg"
// get bitmap from assets folder
        val bitmap: Bitmap? = assetsToBitmap(fileName)
        bitmap?.apply {
            img.setImageBitmap(this)
        }

        val txtOutput : TextView = findViewById(R.id.txtOutput)
        val btn: Button = findViewById(R.id.btnTest)

        btn.setOnClickListener {
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromBitmap(bitmap!!, 0)
            var outputText = ""
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    // Task completed successfully
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        outputText += "$text : $confidence\n"
                    }
                    txtOutput.text = outputText
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                }
        }
    }
    // extension function to get bitmap from assets
    fun Context.assetsToBitmap(fileName: String): Bitmap?{
        return try {
            with(assets.open(fileName)){
                BitmapFactory.decodeStream(this)
            }
        } catch (e: IOException) { null }
    }
}

