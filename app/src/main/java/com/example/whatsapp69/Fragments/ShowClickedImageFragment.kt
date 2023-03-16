package com.example.whatsapp69.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whatsapp69.R
import com.example.whatsapp69.databinding.FragmentShowClickedImageBinding

class ShowClickedImageFragment : Fragment() {
    private lateinit var binding : FragmentShowClickedImageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        binding = FragmentShowClickedImageBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_show_clicked_image,container,false)

        view.setOnClickListener {
            binding.showIMGUSERCLICKED.visibility = View.GONE
        }

        return inflater.inflate(R.layout.fragment_show_clicked_image, container, false)
    }


}