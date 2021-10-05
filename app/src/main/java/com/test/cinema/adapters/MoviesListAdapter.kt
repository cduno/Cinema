package  com.test.cinema.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.cinema.R
import com.test.cinema.databinding.MoviesListItemBinding
import com.test.cinema.models.Movies
import kotlinx.android.extensions.LayoutContainer

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private var mList: List<Movies>? = listOf()
    private var allDataList: List<Movies>? = listOf()

    fun setData(list: List<Movies>) {
        mList = list
        allDataList=mList
        showListByCatagory("all")

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: MoviesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movies_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.movies = mList!![position]
        if(mList!![position].type=="MOVIE")
            holder.itemBinding.thumbIv.setImageResource(R.drawable.ic_movie)
        else
            holder.itemBinding.thumbIv.setImageResource(R.drawable.ic_serie)


    }

    class ViewHolder(var itemBinding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View?
            get() = itemBinding.root
    }

    fun showListByCatagory(category: String){

        // Filter the list by its category and set to recyclerView
        when(category){
            "all" -> {
                this.mList = allDataList
            }

            "MOVIE" -> {
                this.mList = allDataList?.filter { it.type == "MOVIE" }
            }

            "SERIES" -> {
                this.mList = allDataList?.filter { it.type == "SERIES" }
            }

            else ->{
                this.mList = allDataList
            }
        }

        //Notify RecyclerView to change List.
        notifyDataSetChanged()
    }
}