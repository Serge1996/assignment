package pro.onlycode.testassignment.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pro.onlycode.testassignment.data.models.Hit
import pro.onlycode.testassignment.network.NetworkUtil

class MainViewModel : ViewModel() {

    var data: MutableLiveData<MutableList<Hit>> = MutableLiveData<MutableList<Hit>>().apply { value = mutableListOf() }
    var counterPost: Int = 0
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    fun getData(page: Int) {
        compositeDisposable.add(NetworkUtil.retrofit.getData(tags = "story", page = page)
            .map { it ->
                return@map it.hits
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data.value = it as MutableList<Hit> }, { it.printStackTrace() })
        )
        counterPost = data.value?.size!!

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}