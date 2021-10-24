package com.jcy.dessertorderapp.widget.adapter.viewholder

import com.jcy.dessertorderapp.databinding.ViewholderEmptyBinding
import com.jcy.dessertorderapp.model.Model
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener

class EmptyViewHolder(
    private val binding: ViewholderEmptyBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
) : ModelViewHolder<Model>(binding,viewModel,resourceProvider) {
    override fun reset() = Unit

    override fun bindViews(model: Model, adapterListener: AdapterListener) = Unit
}