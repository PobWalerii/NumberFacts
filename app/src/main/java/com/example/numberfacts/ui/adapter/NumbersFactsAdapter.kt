package com.example.numberfacts.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberfacts.R
import com.example.numberfacts.data.database.entitys.Numbers
import com.example.numberfacts.databinding.NumbersListItemBinding

class NumbersFactsAdapter(private val contentType: Boolean): RecyclerView.Adapter<NumbersFactsAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null
    private var list: List<Numbers> = emptyList()
    private var currentId: Long = 0L

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = NumbersListItemBinding.bind(view)
        fun bind(item: Numbers, currentId: Long, contentType: Boolean) {
            binding.item = item
            binding.currentId = currentId
            binding.contentType = contentType
        }
        fun getBinding(): NumbersListItemBinding = binding
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.numbers_list_item, parent, false)
        val holder = ViewHolder(view)
        val binding = holder.getBinding()

        binding.container.setOnClickListener {
            val current = list[holder.adapterPosition]
            currentId = current.id
            notifyDataSetChanged()
            listener?.onItemClick(current)
        }

        return holder
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    interface OnItemClickListener {
        fun onItemClick(current: Numbers)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], currentId, contentType)
    }

    override fun getItemCount(): Int = list.size
    override fun getItemId(position: Int): Long = list[position].id

    fun getItemFromPosition(position: Int): Numbers = list[position]

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<Numbers>, currId: Long) {
        list = newList
        if (currId != -1L) {
            currentId =
                if (list.isEmpty())
                    0
                else if (currId == 0L)
                    list[0].id
                else currId
        }
        notifyDataSetChanged()
    }

}
