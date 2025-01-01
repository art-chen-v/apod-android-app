package com.artemchen.android.apod.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.artemchen.android.apod.database.getImageUrl
import com.artemchen.android.apod.databinding.FragmentApodDetailsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ApodDetailsFragment"

class ApodDetailsFragment : Fragment() {

    private var _binding: FragmentApodDetailsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannnot access binding because it is null. Is the view visible?"
        }

    private val viewModel: ApodDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apodItem.collect { apodItem ->
                    apodItem?.let {
                        binding.apply {
                            apodDetailsImage.load(apodItem.getImageUrl())
                            apodDetailsTitle.text = apodItem.title
                            apodDetailsAuthor.text = apodItem.copyright
                            apodDetailsDate.text = apodItem.date.toString()
                            apodDetailsDescr.text = apodItem.explanation
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}