package com.evonative.vodworks.test.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.evonative.vodworks.test.data.model.ItemData
import com.evonative.vodworks.test.presentation.adapters.HomeActivityListAdapter
import com.evonative.vodworks.test.presentation.adapters.HomeActivityListItemClickListener
import com.evonative.vodworks.test.presentation.model.UIState
import com.evonative.vodworks.test.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import evonative.hilt.coroutines.retrofit.example.R
import evonative.hilt.coroutines.retrofit.example.databinding.ActivityHomeBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel? by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeFlowData()
        homeViewModel?.loadItems()
    }

    private fun observeFlowData() {
        lifecycleScope.launchWhenStarted {
            homeViewModel?.userFlow?.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        showProgress()
                    }
                    is UIState.Success -> {
                        hideProgress()
                        state.data?.let { updateUI(it) } ?: showError()
                    }
                    is UIState.Error -> {
                        hideProgress()
                        showError()
                    }
                }
            }
        }
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError() {
        binding.errorMessage.text = getString(R.string.error_message)
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun updateUI(itemsData: List<ItemData>) {
        with(binding) {
            moviesRecyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
            moviesRecyclerView.adapter = HomeActivityListAdapter(itemsData, this@HomeActivity, object : HomeActivityListItemClickListener{
                override fun onPositionClicked(position: Int) {
                    var intent =  Intent(this@HomeActivity,ItemDetailActivity::class.java);
                        intent.putExtra("ItemPosition",position)
                    startActivity(intent)
                }

            })
        }
    }
}