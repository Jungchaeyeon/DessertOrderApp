package com.jcy.dessertorderapp.screen.review.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.databinding.ActivityGalleryBinding
import com.jcy.dessertorderapp.widget.adapter.GalleryPhotoListAdapter
import com.jcy.dessertorderapp.widget.decoration.GridDividerDecoration
import org.koin.android.viewmodel.ext.android.viewModel

class GalleryActivity : AppCompatActivity() {

    companion object {
        fun newIntent(activity: Activity) = Intent(activity, GalleryActivity::class.java)

        const val URI_LIST_KEY = "uriList"
    }

    private lateinit var binding: ActivityGalleryBinding

    private val adapter = GalleryPhotoListAdapter {
        viewModel.selectPhoto(it)
    }

    private val viewModel by viewModel<GalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        viewModel.fetchData()
        observeState()
    }

    private fun observeState() = viewModel.galleryStateLiveData.observe(this) {
        when (it) {
            is GalleryState.Loading -> handleLoading()
            is GalleryState.Success -> handleSuccess(it)
            is GalleryState.Confirm -> handleConfirm(it)
            else -> Unit
        }
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridDividerDecoration(this@GalleryActivity, R.drawable.bg_frame_gallery))
        confirmButton.setOnClickListener {
            viewModel.confirmCheckedPhotos()
        }
    }

    private fun handleLoading() = with(binding) {
        progressBar.isVisible = true
        recyclerView.isGone = true
    }

    private fun handleSuccess(state: GalleryState.Success) = with(binding) {
        progressBar.isGone = true
        recyclerView.isVisible = true
        adapter.setPhotoList(state.photoList)
    }

    private fun handleConfirm(state: GalleryState.Confirm) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(URI_LIST_KEY, ArrayList(state.photoList.map { it.uri }))
        })
        finish()
    }

}