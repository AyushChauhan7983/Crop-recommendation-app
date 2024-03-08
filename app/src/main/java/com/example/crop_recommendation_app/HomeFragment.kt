package com.example.crop_recommendation_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.VideoView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val videoview = view?.findViewById<VideoView>(R.id.video_view)
        val uri = Uri.parse("android.resource://" + getActivity()?.getPackageName() + "/" + R.raw.video)
        videoview?.setVideoURI(uri)
        videoview?.setOnCompletionListener { mp ->
            mp?.start()
        }
        videoview?.requestFocus()
        videoview?.start()

        val button = view?.findViewById<Button>(R.id.discover)
        val button1 = view?.findViewById<Button>(R.id.readmore)
        button?.setOnClickListener{
            val url = "https://mahadhan.co.in/knowledge-centre/blogs/?_page=3"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        button1?.setOnClickListener{
            val url = "https://mahadhan.co.in/knowledge-centre/blogs/?_page=3"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        return view
    }

}