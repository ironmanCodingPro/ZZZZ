package tn.esprit.curriculumvitae.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import tn.esprit.curriculumvitae.R
import tn.esprit.curriculumvitae.ui.activity.EMAIL
import tn.esprit.curriculumvitae.ui.activity.FULL_NAME

class AboutMeFragment : Fragment() {

    private lateinit var txtFullName: TextView
    private lateinit var txtAge: TextView
    private lateinit var txtGender: TextView
    private lateinit var txtEmail: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView : View = inflater.inflate(R.layout.fragment_about_me, container, false)

        txtFullName = rootView.findViewById(R.id.txtFullName)
        txtAge = rootView.findViewById(R.id.txtAge)
        txtGender = rootView.findViewById(R.id.txtGender)
        txtEmail = rootView.findViewById(R.id.txtEmail)

        txtFullName.isEnabled = false
        txtAge.isEnabled = false
        txtGender.isEnabled = false
        txtEmail.isEnabled = false

        val name = requireArguments().getString(FULL_NAME,"NULL")
        val email = requireArguments().getString(EMAIL,"NULL")

        txtFullName.text = "Hello ! My name is $name"
        txtAge.text = "I have $email        email old"
        txtGender.text = "and I am a $name"
        txtEmail.text = email

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String,  email: String) = AboutMeFragment().apply {
            arguments = Bundle().apply {
                putString(FULL_NAME, name)

                putString(EMAIL, email)
            }
        }
    }
}