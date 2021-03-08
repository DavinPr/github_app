package com.app.githubmobile.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.helper.setImage
import com.app.githubmobile.helper.shortNumberDisplay

class DetailActivity : AppCompatActivity() {

    companion object {
        const val dataKey = "data_key"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: User? = intent.getParcelableExtra(dataKey)

        if (data != null) {
            dataBinding(data)
        }
    }

    private fun dataBinding(user: User) {
        user.avatar?.let { setImage(this, binding.detailAvatar, it) }
        binding.dataContainer.apply {
            detailName.text = user.name
            detailUsername.text = user.username
            val followers = SpannableStringBuilder()
                .bold { append(user.follower?.toDouble()?.shortNumberDisplay().toString()) }
                .append(" followers")
            detailFollowers.text = followers

            val following = SpannableStringBuilder()
                .bold { append(user.following?.toDouble()?.shortNumberDisplay().toString()) }
                .append(" following")
            detailFollowing.text = following

        }
    }


}