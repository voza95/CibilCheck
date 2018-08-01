package com.example.cibilcheck.adapter


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.cibilcheck.R
import com.example.cibilcheck.dto.AppCard

import java.io.File
import java.util.*


class AllAppsAdapter(private val itemsData: ArrayList<AppCard>?, var context: Context) : RecyclerView.Adapter<AllAppsAdapter.ViewHolder>() {
    private val appLogo: ImageView? = null
    internal var path: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // create a new view
        val itemLayoutView = LayoutInflater.from(parent.context)
                .inflate(R.layout.app_card, null)

        // create ViewHolder
        return ViewHolder(itemLayoutView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        val obj = itemsData!![position]
        //int i = obj.getImage();
        holder.title.text = obj.title
        if (obj.thumbnail != null && obj.thumbnail.contains("https://")) {
            //  Log.d("AllAppAdapter", "in if")
            Glide.with(context).load(obj.thumbnail)
                    .apply(RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .override(300, 300)
                            .centerInside()
                            .placeholder(R.drawable.ic_launcher_background)
                            .dontTransform())
                    .into(holder.thumbnail)
        } else {
            // Log.d("AllAppAdapter", "in else")
            holder.thumbnail.setImageURI(Uri.fromFile(File(obj.thumbnail)))
        }
        if (isAppInstalled(obj.getUrl().replace("https://play.google.com/store/apps/details?id=", ""))) {
            holder.installBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.installBtn.text = "Open"
            holder.installBtn.setOnClickListener {
                val LaunchIntent = context.packageManager.getLaunchIntentForPackage(obj.getUrl().replace("https://play.google.com/store/apps/details?id=", ""))
                context.startActivity(LaunchIntent)
            }
        } else {
            holder.installBtn.setOnClickListener {
                val uri = Uri.parse(obj.url)
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                try {
                    context.startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(Intent(Intent.ACTION_VIEW,
                            Uri.parse(obj.url)))
                }
            }
        }
        // Log.d("AllAppAdapter", "Jsoup url " + position + obj.getUrl().replace("https://play.google.com/store/apps/details?id=", ""));

    }

    override fun getItemCount(): Int {
        if (itemsData != null)
            return itemsData.size
        else
            return 0
    }

    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        var title: TextView = itemLayoutView.findViewById<TextView>(R.id.title) as TextView
        var thumbnail: ImageView = itemLayoutView.findViewById<ImageView>(R.id.thumbnail) as ImageView
        var installBtn: Button = itemLayoutView.findViewById<Button>(R.id.installBtn) as Button

    }


    fun isAppInstalled(packName: String): Boolean {
        val packList = context.getPackageManager().getInstalledPackages(0)
        for (i in packList.indices) {
            val packInfo = packList.get(i)
            if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM === 0) {
                val packagename = packInfo.applicationInfo.packageName
                if (packName.equals(packagename)) {
                    return true
                }

                // Log.e("MPackage" + Integer.toString(i), appName)
            }
            // Log.e("MPackage" ,"in for"+ Integer.toString(i))

        }
        return false
    }

}
