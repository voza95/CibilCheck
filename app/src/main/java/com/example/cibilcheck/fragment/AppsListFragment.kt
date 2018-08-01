package com.example.cibilcheck.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.cibilcheck.R
import com.example.cibilcheck.adapter.AllAppsAdapter
import com.example.cibilcheck.dto.AppCard
import com.example.cibilcheck.util.ConnectionDetector
import com.example.cibilcheck.util.MakeNetworkRequest
import com.example.cibilcheck.util.SheetConstant
import com.example.cibilcheck.util.TinyDB


import org.json.JSONObject
import java.util.*


class AppsListFragment : Fragment(), MakeNetworkRequest.GetVolleyRequestCallback {

    var data: ArrayList<AppCard>? = null
    var list: ArrayList<AppCard>? = null
    lateinit var mProgressBar: ProgressBar
    private val TAG: String = "DetailFragment"
    lateinit var recyclerView: RecyclerView
    // var thinDownloadManager: ThinDownloadManager? = null
    var tinydb: TinyDB? = null
    //var context :Context?=null
    //   var urlCategory = ""
    //  var category = ""
    //  var fragmentListner: FragmentListner? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)


        tinydb = TinyDB(context)
        data = tinydb!!.getAppCardListObject("AppLOGO", AppCard::class.java)


        /*  if (context is FragmentListner) {
              fragmentListner = context
          }*/
        // category = arguments!!.getString("CATEGORY")
        // urlCategory = StringBuilder(AppConstant.StartURL).append(category).append(AppConstant.ENDURL).toString()
        //   Log.d("DetailFragment ", "list " +list!!.size )

        //Make Request to get data from server
        var netStatus = ConnectionDetector(context)
        if (netStatus.isConnectingToInternet) {

            MakeNetworkRequest(getContext(), SheetConstant.fullURL, this).sendRequest(MakeNetworkRequest.getRequestQueue())
        } else if (!netStatus.isConnectingToInternet) {
            if (data == null && data!!.size == 0)
                Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_apps_list, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView
        mProgressBar = view.findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        recyclerView.layoutManager = GridLayoutManager(activity, 1) as RecyclerView.LayoutManager?
        recyclerView.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        if (data != null && data!!.size != 0) {
            /* data!!.forEach {
                 getDonwloadedFile(it)
             }*/
            mProgressBar.visibility = View.GONE
            recyclerView.adapter = AllAppsAdapter(data!!, context!!)
            (recyclerView.adapter as AllAppsAdapter).notifyDataSetChanged()

        }

        return view
    }

    companion object {
        @JvmStatic
        var fragment: Fragment? = null;

        @JvmStatic
        fun newInstance(category: String): Fragment {

            if (fragment == null) {
                // Log.d("MyFragment", " frag null ")
                fragment = AppsListFragment()
                return fragment!!
            } else {
                //  Log.d("MyFragment", " frag not null ")
                return AppsListFragment()
            } /*     val args = Bundle()
            args.putString("CATEGORY", category)
            fragment.arguments = args*/


        }
    }

    override fun onGetResponse(response: JSONObject?) {
        //  Log.d("DetailFragment ", "onGetResponse" + response)

        if (response != null) {
            parseJSONAndPrepareList(response)

            /* var obj = AddBannerInList(context, data!!)
             obj.addNativeExpressAds()
             obj.setUpAndLoadNativeExpressAds()*/
        }

    }

    override fun onGetError(error: VolleyError?) {
        //  Log.d("DetailFragment ", "onGetError" + error)
        mProgressBar.visibility = View.GONE
        if (context != null)
            Toast.makeText(getContext(), " Some Error occure ", Toast.LENGTH_SHORT).show()

    }

    private fun parseJSONAndPrepareList(response: JSONObject) {
        try {

            val files = response.getJSONArray("values")
            data = ArrayList<AppCard>()

            for (i in 0 until files.length()) {
                // downloadFile(files.getJSONArray(i).getString(2), files.getJSONArray(i).getString(1), i)
                data!!.add(AppCard(files.getJSONArray(i).getString(0), files.getJSONArray(i).getString(1), files.getJSONArray(i).getString(2)));
            }

            tinydb!!.putAppCardList("AppLOGO", data!!)

            mProgressBar.visibility = View.GONE
            recyclerView.adapter = AllAppsAdapter(data!!, context!!)
            (recyclerView.adapter as AllAppsAdapter).notifyDataSetChanged()


        } catch (e: Exception) {
            //  Log.d(TAG, "JSONException arrry " + e)
            mProgressBar.visibility = View.GONE
            // Toast.makeText(activity, " Some Error occure ", Toast.LENGTH_SHORT).show()

            //    Toast.makeText(activity, " error " + e, Toast.LENGTH_SHORT).show()
            //e.printStackTrace()
        }

    }

/*
    private fun getDonwloadedFile(appcard: AppCard) {
        val fileDir = getDirectory()
        if (fileDir.exists()) {
            val files = fileDir.listFiles()
            if (files != null) {
                for (file in files) {
                    // Log.d("DetailFragment ", "onGetResponse file name " + file.name +" app "+appcard.title )
                    if (file.name.equals(appcard.title + ".png")) {
                        appcard.thumbnail = file.absolutePath
                        //  Log.d("DetailFragment ", "in if file name " + file.name +" app "+appcard.title )

                    }
                }
            }
        }
    }
*/

    /*  private fun downloadFile(url: String, title: String, position: Int) {
          thinDownloadManager = ThinDownloadManager()
          thinDownloadManager!!.add(makeDownloadRequest(url, title, position))
      }

      private fun makeDownloadRequest(url: String, title: String, position: Int): DownloadRequest {

          val destinationURI = Uri.parse(getDirectory().path + "/" + title + ".png")
          return DownloadRequest(Uri.parse(url))
                  .setRetryPolicy(com.thin.downloadmanager.DefaultRetryPolicy())
                  .setDestinationURI(destinationURI)
                  .setStatusListener(object : DownloadStatusListenerV1 {
                      override fun onDownloadComplete(downloadRequest1: DownloadRequest) {
                          //    Toast.makeText(activity, " File Downloaded\n $destinationURI ", Toast.LENGTH_SHORT).show()
                          //   data!!.get(position).thumbnail = getDirectory().absolutePath + "/applogo/" + title + ".png"

                      }

                      override fun onDownloadFailed(downloadRequest1: DownloadRequest, errorCode: Int, errorMessage: String) {
                          //     Toast.makeText(activity, " File Downloaded faild \n $errorMessage ", Toast.LENGTH_SHORT).show()

                      }

                      override fun onProgress(downloadRequest1: DownloadRequest, totalBytes: Long, downloadedBytes: Long, progress: Int) {


                      }
                  })

      }
  */
    /*fun getDirectory(): File {

        val mFolder = File(AppConstant.getDirectory(), "/.applogo")
        if (!mFolder.exists()) {
            mFolder.mkdir()
        } else {
            return mFolder
        }
        return mFolder
    }*/


    override fun onPause() {
        super.onPause()
    }
    /*  interface FragmentListner {
          fun onItemClickListner(category: String, contentsDto: ContentsDto)
      }*/


}
