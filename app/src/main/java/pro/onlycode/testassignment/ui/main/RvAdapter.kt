package pro.onlycode.testassignment.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pro.onlycode.testassignment.R
import pro.onlycode.testassignment.data.models.Hit

class RvAdapter(var appcontext: Context) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {


    var list: MutableList<Hit> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list.get(position).title
        holder.created_at.text = list.get(position).createdAt

    }

    fun setNewData(hits: MutableList<Hit>) {
        list.clear()
        list = hits
        notifyDataSetChanged()
    }

    fun addData(hits: MutableList<Hit>):Int {
        list.addAll(hits)
        notifyDataSetChanged()
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardView: CardView = itemView.findViewById(R.id.cardView)
        var title: TextView = itemView.findViewById(R.id.title)
        var created_at: TextView = itemView.findViewById(R.id.created_at)
    }

}

