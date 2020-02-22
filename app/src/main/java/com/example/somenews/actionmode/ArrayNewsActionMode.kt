package com.example.somenews.actionmode

import android.graphics.Color
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.somenews.R
import com.example.somenews.db.entity.News
import com.example.somenews.model.Article

class ArrayNewsActionMode(
    private val article: Article,
    private val view: View
) : ActionMode.Callback {

    companion object{
        var mActionMode: ActionMode? = null
        val setNewsLiveData = MutableLiveData<News>()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

        return when(item!!.itemId){
            R.id.add_article_in_ldb ->{
                val news = News(
                    article.title,
                    article.urlToImage,
                    article.content,
                    article.publishedAt,
                    article.author,
                    article.url)
                setNewsLiveData.value = news
                mode!!.finish()
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode!!.menuInflater.inflate(R.menu.array_news_context_menu,menu)
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