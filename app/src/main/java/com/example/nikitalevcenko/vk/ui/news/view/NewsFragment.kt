package com.example.nikitalevcenko.vk.ui.news.view


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.repo.NetworkState
import com.example.nikitalevcenko.vk.repo.Status
import com.example.nikitalevcenko.vk.ui.handleError
import com.example.nikitalevcenko.vk.ui.news.di.NewsModule
import com.example.nikitalevcenko.vk.ui.news.vm.INewsViewModel
import com.example.nikitalevcenko.vk.ui.showMessage
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.layout_loader.view.*
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

        val adapter = NewsAdapter(lifecycleOwner = this,
                attachments = viewModel.attachments,
                profile = viewModel.profile,
                group = viewModel.group,
                onLike = { newsItem ->
                    Transformations.switchMap(viewModel.onLike(newsItem), { networkState ->
                        if (networkState?.status == Status.FAILED) {
                            handleError(networkState.throwable)
                        }

                        object : LiveData<NetworkState>() {
                            override fun onActive() {
                                super.onActive()
                                postValue(networkState)
                            }
                        }
                    })
                },
                onComment = { showMessage(R.string.feature_not_implemented) },
                onRepost = { showMessage(R.string.feature_not_implemented) })

        rootView.newsRecyclerView.layoutManager = LinearLayoutManager(context)
        rootView.newsRecyclerView.adapter = adapter

        rootView.swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.newsListing.news.observe(this, Observer { newsPagedList ->
            adapter.submitList(newsPagedList)

            if (newsPagedList == null) return@Observer

            if (newsPagedList.isEmpty() && viewModel.newsListing.networkState.value?.status == Status.RUNNING) {
                rootView.loaderContainer.visibility = View.VISIBLE
            } else {
                rootView.loaderContainer.visibility = View.GONE
            }
        })

        viewModel.newsListing.networkState.observe(this, Observer { state ->
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

        return rootView
    }
}
