package com.example.aripa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class FaqAdapter(private val faqList: List<FaqItem>) :
    RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val faqCardView: MaterialCardView = itemView.findViewById(R.id.faqCardView)
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faqItem = faqList[position]
        holder.questionTextView.text = faqItem.question
        holder.answerTextView.text = faqItem.answer

        // Set visibility based on expansion state
        holder.answerTextView.visibility = if (faqItem.isExpanded) View.VISIBLE else View.GONE

        // Handle click to expand/collapse
        holder.faqCardView.setOnClickListener {
            faqItem.isExpanded = !faqItem.isExpanded
            notifyItemChanged(position) // Notify adapter to rebind this item
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}