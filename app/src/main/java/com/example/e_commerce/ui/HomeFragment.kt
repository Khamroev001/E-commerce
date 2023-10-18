package com.example.e_commerce.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.adapter.RvAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.model.Product
import com.example.e_commerce.model.ProductData
import com.example.e_commerce.networking.APIClient
import com.example.e_commerce.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

lateinit var binding:FragmentHomeBinding
lateinit var api:APIService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        api=APIClient.getInstance().create(APIService::class.java)
        var list = listOf<Product>()
//        var adapter =RvAdapter(requireContext(),list)
//        binding.rv.adapter= adapter
        binding.rv.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        api.getAllProducts().enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                if (response.isSuccessful && response.body() != null)
                   list= response.body()!!.products
                Log.d("LIST",list.toString())
                var adapter =RvAdapter(requireContext(),list)
                binding.rv.adapter= adapter
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })










        return binding.root
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}