package com.example.popup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class FragmentOne : Fragment() {

    private lateinit var callback: (String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputText = view.findViewById<EditText>(R.id.input_text)
        view.findViewById<Button>(R.id.send_data_button).setOnClickListener {
            val data = inputText.text.toString()
            callback(data)
        }
    }

    fun setCallback(callback: (String) -> Unit) {
        this.callback = callback
    }
}
