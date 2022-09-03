package com.KotlinAss.Adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.KotlinAss.R
import com.KotlinAss.Model.UserDetails
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(var mList: ArrayList<UserDetails>, var context:Context): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inflate_data, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val UserDetails : UserDetails = mList[position]
        var fiste = UserDetails.first;
        var last = UserDetails.last
        var name = fiste+" "+last
        var image = UserDetails.image
        var Gender = UserDetails.gender
        var City = UserDetails.city
        var State = UserDetails.state
        var Country = UserDetails.country
        if(image != null){
            Glide.with(context)
                .load(image)
                .into(holder.imageView)
        }else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_background)
        }
        holder.textView.setText(name)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                showDialog(context,name,Gender,City,State,Country)
            }

        })
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: CircleImageView = itemView.findViewById(R.id.product_image)
        val textView: TextView = itemView.findViewById(R.id.name)
    }

    private fun showDialog( context: Context,title: String,Gender: String, City: String, State: String, Country: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_layout)
        val tv1 = dialog.findViewById(R.id.tv1) as TextView
        val tv2 = dialog.findViewById(R.id.tv2) as TextView
        val tv3 = dialog.findViewById(R.id.tv3) as TextView
        val tv4 = dialog.findViewById(R.id.tv4) as TextView
        val tv5 = dialog.findViewById(R.id.tv5) as TextView
        val tvcancel = dialog.findViewById(R.id.tvcancel) as TextView
        val tvOk = dialog.findViewById(R.id.tvOk) as TextView
        tv1.text = title
        tv2.text = Gender
        tv3.text = City
        tv4.text = State
        tv5.text = Country
        tvOk.setOnClickListener {
            dialog.dismiss()
        }
        tvcancel.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }
}