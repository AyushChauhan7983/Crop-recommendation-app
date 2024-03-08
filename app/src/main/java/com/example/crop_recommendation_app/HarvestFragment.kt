package com.example.crop_recommendation_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView

class HarvestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_harvest, container, false)

        val videoview = view?.findViewById<VideoView>(R.id.video_view)
        val uri = Uri.parse("android.resource://" + getActivity()?.getPackageName() + "/" + R.raw.harvestingbg)
        videoview?.setVideoURI(uri)
        videoview?.setOnCompletionListener { mp ->
            mp?.start()
        }
        videoview?.requestFocus()
        videoview?.start()

        val videoview1 = view?.findViewById<VideoView>(R.id.video_view1)
        val uri1 = Uri.parse("android.resource://" + getActivity()?.getPackageName() + "/" + R.raw.harvestvideo)
        videoview1?.setVideoURI(uri1)
        videoview1?.setOnCompletionListener { mp ->
            mp?.start()
        }
        videoview1?.requestFocus()
        videoview1?.start()

        val button = view?.findViewById<Button>(R.id.discover)
        button?.setOnClickListener{
            val url = "https://www.hartwoodfarm.com/farm-blog/tag/harvesting"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val button1 = view?.findViewById<Button>(R.id.calendar)
        button1?.setOnClickListener{
            val url = "https://krishijagran.com/crop-calendar"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val button2 = view?.findViewById<Button>(R.id.seemore)
        button2?.setOnClickListener{
            val url = "https://youtube.com/playlist?list=PLNtb1UTOqaW41tevlOCVs3TokMOMKgsob&si=9Do2mBXC2bprR5xl"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        return view
    }

}