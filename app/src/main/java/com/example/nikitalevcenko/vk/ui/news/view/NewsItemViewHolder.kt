package com.example.nikitalevcenko.vk.ui.news.view

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.api.ApiFields
import com.example.nikitalevcenko.vk.entity.Attachment
import com.example.nikitalevcenko.vk.entity.Group
import com.example.nikitalevcenko.vk.entity.NewsItem
import com.example.nikitalevcenko.vk.entity.Profile
import com.example.nikitalevcenko.vk.repo.NetworkState
import com.example.nikitalevcenko.vk.ui.loadImage
import com.example.nikitalevcenko.vk.ui.toDateString
import kotlinx.android.synthetic.main.item_link.view.*
import kotlinx.android.synthetic.main.item_video.view.*

class NewsItemViewHolder(itemView: View,
                         private val lifecycleOwner: LifecycleOwner,
                         private val attachments: (postId: Long) -> LiveData<List<Attachment>>,
                         private val profile: (id: Long) -> LiveData<Profile>,
                         private val group: (id: Long) -> LiveData<Group>,
                         private val onLike: (newsItem: NewsItem) -> LiveData<NetworkState>,
                         private val onComment: () -> Unit,
                         private val onRepost: () -> Unit)
    : RecyclerView.ViewHolder(itemView) {

    private var newsItem: NewsItem? = null

    private val sourcePhotoImageView = itemView.findViewById<ImageView>(R.id.sourcePhotoImageView)
    private val sourceNameTextView = itemView.findViewById<TextView>(R.id.sourceNameTextView)
    private val sourceDateTextView = itemView.findViewById<TextView>(R.id.sourceDateTextView)

    private val ownerPhotoImageView = itemView.findViewById<ImageView>(R.id.ownerPhotoImageView)
    private val ownerNameTextView = itemView.findViewById<TextView>(R.id.ownerNameTextView)
    private val ownerDateTextView = itemView.findViewById<TextView>(R.id.ownerDateTextView)

    private val ownerLinearLayout = itemView.findViewById<LinearLayout>(R.id.ownerLinearLayout)

    private val newsTextView = itemView.findViewById<TextView>(R.id.newsTextView)
    private val contentFrameLayout = itemView.findViewById<FrameLayout>(R.id.contentFrameLayout)

    private val likesCheckBox = itemView.findViewById<CheckBox>(R.id.likesCheckBox).apply {
        setOnCheckedChangeListener({ _, isChecked ->
            if (newsItem!!.likes.userLikes != isChecked) {
                likeLiveData = onLike(newsItem!!)
                likeLiveData!!.observe(lifecycleOwner, likeObserver)
            }
        })
    }
    private val repostsCheckbox = itemView.findViewById<CheckBox>(R.id.repostsCheckbox).apply {
        setOnClickListener {
            onRepost()
            (it as CheckBox).isChecked = false
        }
    }
    private val commentsCheckBox = itemView.findViewById<CheckBox>(R.id.commentsCheckbox).apply {
        setOnClickListener {
            onComment()
            (it as CheckBox).isChecked = false
        }
    }
    private val viewsTextView = itemView.findViewById<TextView>(R.id.viewsTextView)

    private val screenWidth by lazy {
        itemView.context.resources.displayMetrics.widthPixels
    }

    private var attachmentsLiveData: LiveData<List<Attachment>>? = null

    private val attachmentsObserver by lazy {
        Observer<List<Attachment>> { attachments ->
            contentFrameLayout.removeAllViews()
            if (attachments == null || attachments.isEmpty()) {
                return@Observer
            }

            if (attachments.size == 1) {
                val attachment = attachments[0]

                when (attachment.type) {
                    ApiFields.PHOTO -> {
                        contentFrameLayout.addView(ImageView(itemView.context).apply {
                            this.layoutParams = FrameLayout.LayoutParams(screenWidth, screenWidth * attachment.photo!!.height / attachment.photo!!.width)
                            loadImage(attachment.photo!!.photoUrl, attachment.photo!!.smallPhotoUrl)
                        })
                    }
                    ApiFields.VIDEO -> {
                        LayoutInflater.from(itemView.context).inflate(R.layout.item_video, contentFrameLayout, true).apply {
                            if (attachment.video!!.height != 0 && attachment.video!!.width != 0) {
                                videoImageView.layoutParams = FrameLayout.LayoutParams(screenWidth, screenWidth * attachment.video!!.height / attachment.video!!.width)
                            }
                            videoImageView.loadImage(attachment.video!!.photoUrl, attachment.video!!.smallPhotoUrl)
                        }
                    }
                    ApiFields.LINK -> {
                        LayoutInflater.from(itemView.context).inflate(R.layout.item_link, contentFrameLayout, true).apply {
                            linkImageView.loadImage(attachment.link!!.photo.photoUrl, attachment.link!!.photo.smallPhotoUrl)
                            linkImageView.layoutParams = FrameLayout.LayoutParams(screenWidth, screenWidth * attachment.link!!.photo.height / attachment.link!!.photo.width)
                        }
                    }
                }
            }
        }
    }

    private var sourceProfileLiveData: LiveData<Profile>? = null

    private val sourceProfileObserver by lazy {
        Observer<Profile> { profile ->
            if (profile == null) return@Observer
            sourcePhotoImageView.loadImage(profile.smallPhoto)
            sourceNameTextView.text = itemView.context.getString(R.string.name, profile.firstName, profile.lastName)
        }
    }

    private var sourceGroupLiveData: LiveData<Group>? = null

    private val sourceGroupObserver by lazy {
        Observer<Group> { group ->
            if (group == null) return@Observer
            sourcePhotoImageView.loadImage(group.smallPhoto)
            sourceNameTextView.text = group.name
        }
    }

    private var ownerProfileLiveData: LiveData<Profile>? = null

    private val ownerProfileObserver by lazy {
        Observer<Profile> { profile ->
            if (profile == null) return@Observer
            ownerPhotoImageView.loadImage(profile.smallPhoto)
            ownerNameTextView.text = itemView.context.getString(R.string.name, profile.firstName, profile.lastName)
        }
    }

    private var ownerGroupLiveData: LiveData<Group>? = null

    private val ownerGroupObserver by lazy {
        Observer<Group> { group ->
            if (group == null) return@Observer
            ownerPhotoImageView.loadImage(group.smallPhoto)
            ownerNameTextView.text = group.name
        }
    }

    private var likeLiveData: LiveData<NetworkState>? = null

    private val likeObserver by lazy {
        Observer<NetworkState> { group ->
            bindLikes()
        }
    }

    fun bind(newsItem: NewsItem?) {
        if (newsItem == null) return

        this.newsItem = newsItem

        sourceDateTextView.text = newsItem.sourceDate.toDateString()
        if (newsItem.sourceDate != newsItem.ownerDate) {
            ownerDateTextView.text = newsItem.ownerDate.toDateString()
        }

        if (newsItem.text.isNullOrEmpty()) {
            newsTextView.visibility = View.GONE
        } else {
            newsTextView.visibility = View.VISIBLE
            newsTextView.text = newsItem.text
        }


        // Likes
        bindLikes()


        // Reposts
        if (newsItem.reposts.count == 0) {
            repostsCheckbox.text = ""
        } else {
            repostsCheckbox.text = newsItem.reposts.count.toString()
        }
        repostsCheckbox.isChecked = newsItem.reposts.userReposted


        // Comments
        if (newsItem.comments.count == 0) {
            commentsCheckBox.text = ""
        } else {
            commentsCheckBox.text = newsItem.comments.count.toString()
        }
        commentsCheckBox.isEnabled = newsItem.comments.canPost


        // Views
        if (newsItem.views.count == 0) {
            viewsTextView.visibility = View.GONE
        } else {
            viewsTextView.text = newsItem.views.count.toString()
            viewsTextView.visibility = View.VISIBLE
        }


        // Attachments
        attachmentsLiveData?.removeObserver(attachmentsObserver)
        attachmentsLiveData = attachments(newsItem.postId)
        attachmentsLiveData!!.observe(lifecycleOwner, attachmentsObserver)


        // Source
        sourceProfileLiveData?.removeObserver(sourceProfileObserver)
        sourceGroupLiveData?.removeObserver(sourceGroupObserver)
        if (newsItem.sourceId > 0) {
            sourceProfileLiveData = profile(newsItem.sourceId)
            sourceProfileLiveData!!.observe(lifecycleOwner, sourceProfileObserver)
        } else {
            sourceGroupLiveData = group(-newsItem.sourceId)
            sourceGroupLiveData!!.observe(lifecycleOwner, sourceGroupObserver)
        }

        bindOwner()
    }

    private fun bindLikes() {
        likeLiveData?.removeObserver(likeObserver)

        if (newsItem!!.likes.count == 0) {
            likesCheckBox.text = ""
        } else {
            likesCheckBox.text = newsItem!!.likes.count.toString()
        }

        likesCheckBox.isChecked = newsItem!!.likes.userLikes
    }

    private fun bindOwner() {
        ownerGroupLiveData?.removeObserver(ownerGroupObserver)
        ownerProfileLiveData?.removeObserver(ownerProfileObserver)

        if (newsItem!!.sourceId == newsItem!!.ownerId) {
            ownerLinearLayout.visibility = View.GONE
        } else {
            if (newsItem!!.ownerId > 0) {
                ownerProfileLiveData = profile(newsItem!!.ownerId)
                ownerProfileLiveData!!.observe(lifecycleOwner, ownerProfileObserver)
            } else {
                ownerGroupLiveData = group(-newsItem!!.ownerId)
                ownerGroupLiveData!!.observe(lifecycleOwner, ownerGroupObserver)
            }
            ownerLinearLayout.visibility = View.VISIBLE
        }
    }
}