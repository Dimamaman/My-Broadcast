package uz.gita.dima.mybroadcast

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dima.mybroadcast.databinding.ItemLayoutBinding

class MyAdapter: ListAdapter<EventData,MyAdapter.MyViewHolder>(DIFF_CALL_BACK) {

    private var clickChecked: ((EventData) -> Unit)? = null
    fun setClickChecked(block: (EventData) -> Unit) {
        clickChecked = block
    }

    inner class MyViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor", "ResourceType")
        fun bind(myData: EventData) {
            binding.apply {
                text.text = myData.text
                if (myData.isChecked) {
                    image.setImageResource(myData.imageChecked)
                    text.setTextColor(Color.BLACK)
                } else {
                    image.setImageResource(myData.imageNotChecked)
                    text.setTextColor("${Color.GRAY}".toInt())
                }

                clickSwitch.isChecked = myData.isChecked
                clickSwitch.textOn = "On"
                clickSwitch.textOff = "Off"

                clickSwitch.setOnCheckedChangeListener { compoundButton, checked ->
                    if (checked) {
                        myData.isChecked = true
                        text.setTextColor(Color.BLACK)
                        image.setImageResource(myData.imageChecked)
                        clickChecked?.invoke(myData)
                    } else {
                        myData.isChecked = false
                        text.setTextColor("${Color.GRAY}".toInt())
                        image.setImageResource(myData.imageNotChecked)
                        clickChecked?.invoke(myData)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<EventData>() {
            override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean {
                return oldItem == newItem
            }
        }
    }
}