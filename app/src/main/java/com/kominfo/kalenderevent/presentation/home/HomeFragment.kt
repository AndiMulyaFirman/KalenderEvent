package com.kominfo.kalenderevent.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kominfo.kalenderevent.R
import com.kominfo.kalenderevent.presentation.notif.NotifikasiActivity
import com.kominfo.kalenderevent.response.ResponseItem
import com.kominfo.kalenderevent.ui.adapter.EventAdapter

class HomeFragment : Fragment() {
    private val originalEventList = mutableListOf<ResponseItem>()
    private val filteredEventList = mutableListOf<ResponseItem>()
    private lateinit var eventAdapter: EventAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        eventAdapter = EventAdapter(filteredEventList)
        recyclerView.adapter = eventAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Mengambil data dari Firestore
        db.collection("event")
            .get()
            .addOnSuccessListener { result ->
                originalEventList.clear()
                for (document in result) {
                    val event = document.toObject(ResponseItem::class.java)
                    originalEventList.add(event)
                }
                filteredEventList.addAll(originalEventList)
                eventAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }

        // Set click listener for SearchView
        val searchView: SearchView = rootView.findViewById(R.id.search_view)

        // Set up the SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText)
                return true
            }
        })

        // Set click listener for bellIcon
        val bellIcon: ImageView = rootView.findViewById(R.id.bellIcon)
        bellIcon.setOnClickListener {
            // Navigate to the NotifikasiActivity
            startActivity(Intent(requireContext(), NotifikasiActivity::class.java))
        }

        return rootView
    }

    private fun filterData(query: String?) {
        filteredEventList.clear()

        if (!query.isNullOrBlank()) {
            for (item in originalEventList) {
                if (item.nama?.contains(query, true) == true) {
                    filteredEventList.add(item)
                }
            }
        } else {
            filteredEventList.addAll(originalEventList)
        }

        eventAdapter.notifyDataSetChanged()
    }
}
