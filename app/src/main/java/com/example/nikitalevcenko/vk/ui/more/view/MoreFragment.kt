package com.example.nikitalevcenko.vk.ui.more.view


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.receivers.NetworkConnectionListener
import com.example.nikitalevcenko.vk.repo.Status
import com.example.nikitalevcenko.vk.ui.handleError
import com.example.nikitalevcenko.vk.ui.loadIcon
import com.example.nikitalevcenko.vk.ui.more.di.MoreModule
import com.example.nikitalevcenko.vk.ui.more.vm.IMoreViewModel
import com.example.nikitalevcenko.vk.ui.showMessage
import kotlinx.android.synthetic.main.fragment_more.view.*
import kotlinx.android.synthetic.main.layout_loader.view.*
import javax.inject.Inject

class MoreFragment : Fragment(), LifecycleOwner {

    @Inject
    lateinit var viewModel: IMoreViewModel

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    private val networkConnectionObserver by lazy {
        Observer<Boolean> { isConnected ->
            viewModel.onNetworkConnectionChanged(isConnected!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.plus(MoreModule(this)).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_more, container, false)

        viewModel.profileLisitng.profile.observe(this, Observer { profile ->
            if (profile == null) {
                if (viewModel.profileLisitng.networkState.value?.status == Status.RUNNING) {
                    rootView.loaderContainer.visibility = View.VISIBLE
                }
            } else {
                rootView.loaderContainer.visibility = View.GONE
                rootView.photoImageView.loadIcon(profile.smallPhoto)
                rootView.nameTextView.text = context?.getString(R.string.name, profile.firstName, profile.lastName)
            }
        })

        viewModel.profileLisitng.networkState.observe(this, Observer { state ->
            when (state?.status) {
                Status.SUCCESS -> {
                    rootView.loaderContainer.visibility = View.GONE
                    rootView.swipeRefreshLayout.isRefreshing = false
                }
                Status.FAILED -> {
                    rootView.loaderContainer.visibility = View.GONE
                    rootView.swipeRefreshLayout.isRefreshing = false
                    handleError(state.throwable)
                }
            }
        })

        rootView.swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        rootView.profileCardView.setOnClickListener({ viewModel.onGoToProfileCLick() })

        rootView.navigationView.setNavigationItemSelectedListener {
            showMessage(R.string.feature_not_implemented)
            return@setNavigationItemSelectedListener true
        }

        networkConnectionListener.isConnected.observe(this, networkConnectionObserver)

        lifecycleRegistry.markState(Lifecycle.State.CREATED)

        return rootView
    }

    override fun onDestroy() {
        networkConnectionListener.isConnected.removeObserver(networkConnectionObserver)
        super.onDestroy()
    }
}
