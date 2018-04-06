package com.example.nikitalevcenko.vk.modules.more.view


import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.modules.more.di.MoreModule
import com.example.nikitalevcenko.vk.modules.more.vm.IMoreViewModel
import kotlinx.android.synthetic.main.fragment_more.view.*
import javax.inject.Inject

class MoreFragment : Fragment() {

    @Inject
    lateinit var viewModel: IMoreViewModel

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.plus(MoreModule(this)).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_more, container, false)

        rootView.goToProfileButton.setOnClickListener { viewModel.onGoToProfileCLick() }

        return rootView
    }

}
