package com.example.nikitalevcenko.vk.ui.auth.view

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.nikitalevcenko.vk.App
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.ui.auth.di.AuthModule
import com.example.nikitalevcenko.vk.ui.auth.vm.IAuthViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.layout_loader.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

private const val AUTH_URL = "https://oauth.vk.com/authorize?client_id=6417543&display=page&scope=friends&response_type=token&scope=8194&v=5.73"

private const val ACCESS_TOKEN = "access_token"
private const val USER_ID = "user_id"

class AuthActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModel: IAuthViewModel

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        App.component.plus(AuthModule(this)).inject(this)

        loaderContainer.visibility = View.VISIBLE

        loginWebView.loadUrl(AUTH_URL)

        loginWebView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loaderContainer.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url: String? = request?.url.toString()

                if (url == null) super.shouldOverrideUrlLoading(view, request)

                if (url!!.contains(ACCESS_TOKEN, true)) {
                    var accessToken = url.substring(url.indexOf(ACCESS_TOKEN)
                            + ACCESS_TOKEN.length
                            + 1)

                    accessToken = accessToken.substring(0, accessToken.indexOf('&'))

                    val userId = url.substring(url.indexOf(USER_ID) + USER_ID.length + 1, url.length)

                    viewModel.onAccessTokenReceived(accessToken, userId.toLong())

                    return false
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(this.navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}
