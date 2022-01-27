package com.evonative.vodworks.test.presentation.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.asksira.loopingviewpager.LoopingViewPager
import com.asksira.loopingviewpager.indicator.CustomShapePagerIndicator
import com.evonative.vodworks.test.data.model.ItemData
import com.evonative.vodworks.test.others.Utils
import com.evonative.vodworks.test.presentation.adapters.ItemDetailPagerAdapter
import com.evonative.vodworks.test.presentation.model.UIState
import com.evonative.vodworks.test.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import evonative.hilt.coroutines.retrofit.example.R
import evonative.hilt.coroutines.retrofit.example.databinding.ActivityHomeBinding
import evonative.hilt.coroutines.retrofit.example.databinding.ActivityItemDetailBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding
    private val homeViewModel: HomeViewModel? by viewModels()

    private var itemPosition: Int = 0

    private var adapter: ItemDetailPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemPosition = intent.getIntExtra("ItemPosition",0)
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
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar2.visibility = View.GONE
    }

    private fun showError() {
        binding.errorMessage2.text = getString(R.string.error_message)
        binding.errorMessage2.visibility = View.VISIBLE
    }

    private fun updateUI(itemsData: List<ItemData>) {
        val item : ItemData = itemsData.get(itemPosition)
        with(binding) {
            adapter = ItemDetailPagerAdapter(item.cardImages, true)
            viewpager.adapter = adapter

            //Custom bind indicator
            indicator.highlighterViewDelegate = {
                val highlighter = View(this@ItemDetailActivity)
                highlighter.layoutParams = FrameLayout.LayoutParams(Utils.convertPixelsToDp(16), Utils.convertPixelsToDp(2))
                highlighter.setBackgroundColor(Color.parseColor("#ffffff"))
                highlighter
            }
            indicator.unselectedViewDelegate = {
                val unselected = View(this@ItemDetailActivity)
                unselected.layoutParams = LinearLayout.LayoutParams(Utils.convertPixelsToDp(16),Utils.convertPixelsToDp(2))
                unselected.setBackgroundColor(Color.parseColor("#ffffff"))
                unselected.alpha = 0.4f
                unselected
            }
            viewpager.onIndicatorProgress = { selectingPosition, progress -> indicator.onPageScrolled(selectingPosition, progress) }
//            viewpager.pauseAutoScroll();
            indicator.updateIndicatorCounts(viewpager.indicatorCount)


            tv1.text = item.headline
            tv2.text = item.reviewAuthor
            tv3.text = "Quote: ${item.quote}"
            tv4.text = "Last Updated: ${item.lastUpdated}"
            tv5.text = item.body
        }
    }

    override fun onResume() {
        super.onResume()
//        binding.viewpager.resumeAutoScroll()
    }

    override fun onPause() {
//        binding.viewpager.pauseAutoScroll()
        super.onPause()
    }

}