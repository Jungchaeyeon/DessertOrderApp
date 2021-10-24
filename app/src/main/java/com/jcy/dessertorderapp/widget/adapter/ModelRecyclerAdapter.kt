package com.jcy.dessertorderapp.widget.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jcy.dessertorderapp.model.CellType
import com.jcy.dessertorderapp.model.Model
import com.jcy.dessertorderapp.screen.base.BaseViewModel
import com.jcy.dessertorderapp.util.mapper.ModelViewHolderMapper
import com.jcy.dessertorderapp.util.provider.ResourceProvider
import com.jcy.dessertorderapp.widget.adapter.listener.AdapterListener
import com.jcy.dessertorderapp.widget.adapter.viewholder.ModelViewHolder

class ModelRecyclerAdapter<M: Model, VM: BaseViewModel> (
    private var modelList: List<Model>,
    private var viewModel: VM,
    private val resouceProvider: ResourceProvider,
    private val adapterListener: AdapterListener
): ListAdapter<Model, ModelViewHolder<M>>(Model.DIFF_CALLBACK){

    override fun getItemCount(): Int = modelList.size

    override fun getItemViewType(position: Int): Int = modelList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder<M> {
        return ModelViewHolderMapper.map(parent, CellType.values()[viewType], viewModel, resouceProvider)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ModelViewHolder<M>, position: Int) {
        with(holder){
            bindData(modelList[position] as M)
            bindViews(modelList[position] as M,adapterListener)
        }
    }

    override fun submitList(list: MutableList<Model>?) {
        list?.let { modelList = it }
        super.submitList(list)
    }

}
