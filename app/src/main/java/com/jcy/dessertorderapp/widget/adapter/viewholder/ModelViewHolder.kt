package com.jcy.dessertorderapp.widget.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jcy.dessertorderapp.model.Model
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener

abstract class ModelViewHolder<M: Model> (
    binding: ViewBinding,
    protected val viewModel: BaseViewModel,
    protected val resourceProvider: ResourceProvider
    ): RecyclerView.ViewHolder(binding.root){

        abstract fun reset()

        open fun bindData(model: M){
            reset()
        }
        abstract fun bindViews(model: M, adapterListener: AdapterListener)
    }