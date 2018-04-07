package com.example.nikitalevcenko.vk.modules.main.vm

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.view.MenuItem
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.repo.IAuthRepo
import com.example.nikitalevcenko.vk.router.AUTH_MODULE
import com.example.nikitalevcenko.vk.router.MORE_MODULE
import com.example.nikitalevcenko.vk.router.NEWS_MODULE
import ru.terrakok.cicerone.Router

class MainViewModel : ViewModel(), IMainViewModel {

    lateinit var router: Router
    lateinit var authRepo: IAuthRepo

    private val isAuthorized by lazy {
        authRepo.isAuthorized()
    }

    override val title by lazy {
        MutableLiveData<Int>()
    }

    override val currentItemId by lazy {
        MutableLiveData<Int>()
    }

    // LifecycleObserver
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        isAuthorized.observeForever { isAuthorized ->
            if (isAuthorized == null) return@observeForever

            if (isAuthorized) {
                if (currentItemId.value == null) {
                    title.value = R.string.title_news
                    currentItemId.value = R.id.navigation_news
                    router.newRootScreen(NEWS_MODULE)
                }
            } else {
                router.newRootScreen(AUTH_MODULE)
            }
        }
    }

    // IMainViewModel
    override fun onNavigationItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.navigation_news -> {
                title.value = R.string.title_news
                currentItemId.value = R.id.navigation_news
                router.backTo(NEWS_MODULE)
            }
            R.id.navigation_more -> {
                title.value = R.string.title_more
                currentItemId.value = R.id.navigation_more
                router.navigateTo(MORE_MODULE)
            }
        }
    }

    override fun onBackPressed() {
        if (currentItemId.value != R.id.navigation_news) {
            title.value = R.string.title_news
            currentItemId.value = R.id.navigation_news
        }
        router.exit()
    }
}