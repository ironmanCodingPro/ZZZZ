package tn.esprit.curriculumvitae.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import tn.esprit.curriculumvitae.R

const val FULL_NAME = "FULL_NAME"
const val EMAIL = "EMAIL"
const val IMAGE = "IMAGE"

const val IS_REMEMBRED = "IS_REMEMBRED"

class FirstActivity : AppCompatActivity() {

    private var profilePic: ImageView? = null

    private var txtFullName: TextInputEditText? = null
    private var txtAge: TextInputEditText? = null
    private var txtEmail: TextInputEditText? = null

    private var txtLayoutFullName: TextInputLayout? = null
    private var txtLayoutAge: TextInputLayout? = null
    private var txtLayoutEmail: TextInputLayout? = null

    private var rbMale: RadioButton? = null
    private var rbFemale: RadioButton? = null

    private var btnNext: Button? = null

    private var selectedImageUri: Uri? = null

    lateinit var mSharedPref: SharedPreferences
    lateinit var cbRememberMe2: CheckBox

    private val startForResultOpenGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data!!.data
                profilePic!!.setImageURI(selectedImageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        title = getString(R.string.createYourResume)

        profilePic = findViewById(R.id.profilePic)

        txtFullName = findViewById(R.id.txtFullName)
        txtEmail = findViewById(R.id.txtEmail)

        txtLayoutFullName = findViewById(R.id.txtLayoutFullName)
        txtLayoutEmail = findViewById(R.id.txtLayoutEmail)

        btnNext = findViewById(R.id.btnNext)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        if (mSharedPref.getBoolean(IS_REMEMBRED, false)) {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

        profilePic!!.setOnClickListener {
            openGallery()
        }


        cbRememberMe2 = findViewById(R.id.cbRememberMe2)


        btnNext!!.setOnClickListener {



            mSharedPref.edit().apply{
                putString(FULL_NAME, intent.getStringExtra(FULL_NAME).toString())
                putString(EMAIL, intent.getStringExtra(EMAIL).toString())


                putString(IMAGE, intent.getStringExtra(IMAGE).toString())

                putBoolean(IS_REMEMBRED, cbRememberMe2.isChecked)

            }.apply()

            if (cbRememberMe2.isChecked){
                mSharedPref.edit().apply{
                    putBoolean(IS_REMEMBRED, cbRememberMe2.isChecked)
                }.apply()
            }
            clickNext()






        }

    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startForResultOpenGallery.launch(intent)
    }

    private fun clickNext() {
        if (validate()) {

            val mainIntent = Intent(this, SecondActivity::class.java).apply {
                putExtra(FULL_NAME, txtFullName!!.text.toString())
                putExtra(EMAIL, txtEmail!!.text.toString())
                putExtra(IMAGE, selectedImageUri.toString())

            }
            startActivity(mainIntent)
            finish()

        }
    }

    private fun validate(): Boolean {
        txtLayoutFullName!!.error = null
        txtLayoutEmail!!.error = null

        if (selectedImageUri == null) {
            Snackbar.make(
                findViewById(R.id.constraint_Layout),
                "Please select a picture !",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(getColor(R.color.colorPrimaryFade2)).show()
            return false
        }

        if (txtFullName?.text!!.isEmpty()) {
            txtLayoutFullName!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (txtFullName?.length()!! < 3) {
            txtLayoutFullName!!.error = getString(R.string.mustBeAtLeast3)
            return false
        }

        if (txtEmail?.text!!.isEmpty()) {
            txtLayoutEmail!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (!EMAIL_ADDRESS.matcher(txtEmail?.text!!).matches()) {
            txtLayoutEmail!!.error = getString(R.string.checkYourEmail)
            return false
        }



        return true
    }
}