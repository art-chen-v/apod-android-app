package com.artemchen.android.apod.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.artemchen.android.apod.databinding.FragmentApodGalleryBinding
import com.artemchen.android.apod.ui.detail.ApodDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ApodGalleryFragment"

class ApodGalleryFragment : Fragment() {
    private var _binding: FragmentApodGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannnot access binding because it is null. Is the view visible?"
        }

    private val apodGalleryViewModel: ApodGalleryViewModel by viewModels()
    private val apodDetailsViewModel: ApodDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apodPagingAdapter = ApodPagingAdapter { apodItem ->
            apodDetailsViewModel.onApodItemClicked(apodItem)
            findNavController().navigate(
                ApodGalleryFragmentDirections.showApodDetail()
            )
        }

        val loadStateAdapter = ApodGalleryLoadStateAdapter { apodPagingAdapter.retry() }

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if ((position == 0) ||
                    (apodPagingAdapter.itemCount == position && loadStateAdapter.itemCount > 0)
                ) {
                    return 2
                }
                return 1
            }
        }

        binding.photoGrid.adapter = apodPagingAdapter.withLoadStateFooter(loadStateAdapter)
        binding.photoGrid.setHasFixedSize(true)
        binding.photoGrid.layoutManager = gridLayoutManager

        binding.loadingErrorTryAgain.tryAgainBtn.setOnClickListener {
            apodPagingAdapter.retry()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    apodPagingAdapter.loadStateFlow.collectLatest {
                        binding.indeterminateBar.isVisible = it.refresh is LoadState.Loading
                        binding.photoGrid.isVisible = it.refresh !is LoadState.Loading
                        binding.loadingErrorTryAgain.root.isVisible = it.refresh is LoadState.Error
                    }
                }

                apodGalleryViewModel.apodItems.collectLatest {
                    apodPagingAdapter.submitData(it)
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            apodPagingAdapter.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}