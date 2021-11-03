package com.jcy.dessertorderapp.screen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jcy.dessertorderapp.R
import com.jcy.dessertorderapp.databinding.ActivityMainBinding
import com.jcy.dessertorderapp.screen.main.home.HomeFragment
import com.jcy.dessertorderapp.screen.main.my.MyFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }
    private fun initViews() = with(binding){
        bottomNav.setOnNavigationItemSelectedListener(this@MainActivity)
        showFragment(HomeFragment.newInstance(),HomeFragment.TAG)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_home ->{
                showFragment(HomeFragment.newInstance(),HomeFragment.TAG)
                true
            }
            R.id.menu_my ->{
                showFragment(MyFragment.newInstance(),MyFragment.TAG)
                true
            }
            else -> false
        }
    }
    private fun showFragment(fragment: Fragment, tag: String){
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commit()
        }
        findFragment?.let{
            supportFragmentManager.beginTransaction().show(it).commit()
        }?: kotlin.run{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,fragment,tag)
                .commitAllowingStateLoss()
        }
    }
}