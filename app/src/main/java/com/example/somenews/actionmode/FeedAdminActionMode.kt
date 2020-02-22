package com.example.somenews.actionmode

import android.graphics.Color
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.somenews.R
import com.example.somenews.item.LocalNewsItem

class FeedAdminActionMode(
    private val newsItem: LocalNewsItem,
    private val view: View
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
        view.setBackgroundColor(Color.LTGRAY)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        view.setBackgroundColor(Color.TRANSPARENT)
        mActionMode = null
    }

}