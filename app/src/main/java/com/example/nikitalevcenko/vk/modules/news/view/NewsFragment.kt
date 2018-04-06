package com.example.nikitalevcenko.vk.modules.news.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.news.di.NewsModule
import com.example.nikitalevcenko.vk.modules.news.vm.INewsViewModel
import kotlinx.android.synthetic.main.fragment_news.view.*
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModel: INewsViewModel

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.plus(NewsModule(this)).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_news, container, false)

        rootView.goToProfileButton.setOnClickListener { viewModel.onGoToProfileCLick() }

        return rootView
    }
}
