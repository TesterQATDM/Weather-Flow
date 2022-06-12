package com.example.weather.screens.cityList

import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.weather.R
import com.example.weather.databinding.CityItemBinding
import kotlinx.android.synthetic.main.city_item.view.*

interface CityActionListener{

    fun details(cityName: String)

    fun deleteCity(cityName: String)
}

class CityAdapter(
    private val actionListener: CityActionListener
) : RecyclerView.Adapter<CityAdapter.MyViewHolder>(), View.OnClickListener {

        var cities: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onClick(v: View) {

        if (v.tag is String)
            when(v.id) {
                R.id.textView ->
                    if (v.findViewById<TextView>(R.id.textView).text.equals("Список городов"))
                        Toast.makeText(v.context, "Список городов", Toast.LENGTH_SHORT).show()
                R.id.delCity -> {
                    val city = v.tag as String
                    actionListener.deleteCity(city)
                }
                else -> {
                    val city = v.tag as String
                    actionListener.details(city)
                }
            }
        else
            if (v.findViewById<TextView>(R.id.textView).text.equals("Список городов"))
                Toast.makeText(v.context, "Список городов", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(v.context, cities.size.toString(), Toast.LENGTH_SHORT).show()

    }

    open class MyViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class CityViewHolder(binding: CityItemBinding) : MyViewHolder(binding)

        class HeaderViewHolder(binding: CityItemBinding) : MyViewHolder(binding)

        class FooterViewHolder(binding: CityItemBinding) : MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when(holder){
            is MyViewHolder.CityViewHolder -> {
                val cityName = cities[position - 1]
                holder.itemView.tag = cityName
                holder.itemView.delCity.tag = cityName
                holder.itemView.textView.text = cityName
            }
            is MyViewHolder.HeaderViewHolder -> {
                holder.itemView.delCity.visibility = View.GONE
                holder.itemView.textView.text = "Список городов"
                holder.itemView.textView.gravity = Gravity.CENTER
            }
            is MyViewHolder.FooterViewHolder -> {
                val size = cities.size
                holder.itemView.delCity.visibility = View.GONE
                holder.itemView.textView.text = "К-во городов $size"
                holder.itemView.textView.gravity = Gravity.CENTER
            }
        }
    }

    override fun getItemCount() = cities.size + 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.delCity.setOnClickListener(this)
        return when(viewType) {
            TYPE_HEADER -> MyViewHolder.HeaderViewHolder(binding)
            TYPE_FOOTER -> MyViewHolder.FooterViewHolder(binding)
            TYPE_CITY -> MyViewHolder.CityViewHolder(binding)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> TYPE_HEADER
            itemCount - 1 -> TYPE_FOOTER
            else -> TYPE_CITY
        }
    }

    companion object{
        private const val TYPE_HEADER = 0
        private const val TYPE_CITY = 1
        private const val TYPE_FOOTER = 2
    }
}