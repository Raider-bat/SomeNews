package com.example.somenews.actionmode

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import com.example.somenews.R
import com.example.somenews.item.LocalNewsItem

class FeedAdminActionMode(
    private val newsItem: LocalNewsItem
): ActionMode.Callback {
    companion object{
        var mActionMode: ActionMode? = null
        val deleteNewsLiveData = MutableLiveData<LocalNewsItem>()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
         return when(item!!.itemId){
            R.id.delete_news_in_ldb ->{
               deleteNewsLiveData.value = newsItem
                mode!!.finish()
                true
            }
             else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
       mode!!.menuInflater.inflate(R.menu.feed_admin_context_menu,menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mActionMode = null
    }

}