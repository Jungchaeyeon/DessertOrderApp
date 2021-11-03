package com.jcy.dessertorderapp.screen.main.my

import com.jcy.dessertorderapp.databinding.FragmentHomeBinding
import com.jcy.dessertorderapp.databinding.FragmentMyBinding
import com.jcy.dessertorderapp.screen.base.BaseFragment
import com.jcy.dessertorderapp.screen.main.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MyFragment : BaseFragment<MyViewModel,FragmentMyBinding>() {
    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun observeData() {}
    companion object{
        fun newInstance() = MyFragment()

        const val TAG = "MyFragment"
    }
}