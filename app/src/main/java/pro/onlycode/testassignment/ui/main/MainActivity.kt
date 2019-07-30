package pro.onlycode.testassignment.ui.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import pro.onlycode.testassignment.R
import pro.onlycode.testassignment.common.EndlessRecyclerOnScrollListener
import pro.onlycode.testassignment.data.models.Hit
import pro.onlycode.testassignment.ui.LifecycleActivity

class MainActivity : LifecycleActivity() {
    lateinit var mainViewModel: MainViewModel
    var counter: Int = 1
    lateinit var adapter: RvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        supportActionBar?.setTitle(mainViewModel.counterPost)
        adapter = this.let { RvAdapter(it) }

        rv.let {
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.adapter = adapter
            it.addOnScrollListener(
                object : EndlessRecyclerOnScrollListener() {
                    override fun onLoadMore() {
                        loadData()
                    }
                })
        }
        val emptylist: MutableList<List<Hit>> = mutableListOf()
        if (mainViewModel.data.value?.equals(emptylist)!!) {
            loadData()
        }
        mainViewModel.data.observe(this, Observer {
            var counter = adapter.addData(it)
            counterTitle.text = "$counter items"
        })

    }

    fun loadData() {
        mainViewModel.getData(counter)
        counter++
    }
}
