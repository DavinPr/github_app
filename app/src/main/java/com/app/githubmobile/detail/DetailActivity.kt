package com.app.githubmobile.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.coremodule.domain.usecase.model.User
import com.app.githubmobile.databinding.ActivityDetailBinding
import com.app.githubmobile.helper.setImage

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
        binding.dataContainer.detailName.text = user.name
        binding.dataContainer.detailUsername.text = user.username
    }


}