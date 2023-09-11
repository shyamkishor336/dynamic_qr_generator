package com.generator.dynamicqr

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.generator.dynamicqr.qr.code.generator.QRCodeGen
import kotlinx.android.synthetic.main.activity_main.*

private const val PERMISSION_REQUEST = 10
class MainActivity : AppCompatActivity() {
    val qr = QRCodeGen()
    private var permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        try{
            write_text.afterTextChanged { generateQRCode(write_text.text.toString()) }
//            generateQRCode()
        }catch (e:Exception){
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
        }

    }
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
    @SuppressLint("SetTextI18n")
    private fun generateQRCode( text: String){

        val bitmap = qr.encodeAsBitmap(text,400, 400, context)
        Toast.makeText(this, getString(R.string.qr_code_generated_successful_message), Toast.LENGTH_LONG).show()
        iv_qr_code.setImageBitmap(bitmap)
        /*
            The code below will display the encoded credentials to a textView but in your production project do not show them.
         */
        encode_text.text = text
//        encode_text.text = "Encoded credentials\n\nID: ${ID}\nNAME: ${NAME}\nCLOUD: ${CLOUD}\nLIBRARY: $LIBRARY\nDEVELOPER: $DEVELOPER"
    }

    companion object{
        val ID = "65823894032"
        val NAME = "Kotlin Android"
        val CLOUD = "GCP, AWS, Azure"
        val LIBRARY = "ZXing"
        val DEVELOPER = "Emmanuel"
    }

}