package com.example.e_commerce.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.adapter.CategoryAdapter
import com.example.e_commerce.adapter.RvAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.model.MyBottomSheet
import com.example.e_commerce.model.Product
import com.example.e_commerce.model.ProductData
import com.example.e_commerce.networking.APIClient
import com.example.e_commerce.networking.APIService
import com.example.e_commerce.utils.SharedPrefHelper
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
        var lastSearch = ""
//        var adapter =RvAdapter(requireContext(),list)
//        binding.rv.adapter= adapter



        binding.homeCategoryRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        binding.homeFilterFab.setOnClickListener {
            if (binding.homeCategoryRv.isVisible) binding.homeCategoryRv.visibility = View.GONE
            else binding.homeCategoryRv.visibility = View.VISIBLE
        }

        binding.cartBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        }

        api.getCategories().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val categories = response.body()!!
                binding.homeCategoryRv.adapter = CategoryAdapter(
                    categories,
                    requireContext(),
                    binding.homeCategoryRv,
                    object : CategoryAdapter.CategoryPressed {
                        override fun onPressed(category: String) {
                            if (category == "") {
                                api.getAllProducts().enqueue(object : Callback<ProductData>{
                                    override fun onResponse(
                                        call: Call<ProductData>,
                                        response: Response<ProductData>
                                    ) {
                                        val products = response.body()!!.products
                                        changeProductsAdapter(products)
                                    }

                                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                                        Log.d("TAG", "$t")
                                    }

                                })
                            } else {
                                api.getByCategory(category).enqueue(object : Callback<ProductData> {
                                    override fun onResponse(
                                        call: Call<ProductData>,
                                        response: Response<ProductData>
                                    ) {
                                        val products = response.body()?.products!!
                                        changeProductsAdapter(products)
                                    }

                                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                                        Log.d("TAG", "$t")
                                    }
                                })
                            }
                        }

                    })
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {

            }

        })









        binding.rv.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        api.getAllProducts().enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                if (response.isSuccessful && response.body() != null)
                   list= response.body()!!.products
                Log.d("LIST",list.toString())
                var adapter =RvAdapter(requireContext(),list, object : RvAdapter.myInterface{
                    override fun onclick(products: Product) {
                        var bundle= bundleOf("product" to products)
                        findNavController().navigate(R.id.action_homeFragment_to_productFragment,bundle)
                    }

                    override fun addtoCart(products: Product) {
                        val bundle = Bundle()
                        bundle.putInt("quantity", 1)
                        bundle.putSerializable("product", products)
                        findNavController().navigate(R.id.action_homeFragment_to_cartFragment, bundle)

                    }

                }, object : MyBottomSheet.BottomSheetInterface{
                    override fun onAdd(product: Product, quantity: Int) {
                        val shared = SharedPrefHelper.getInstance(requireContext())
                        val bundle = Bundle()
                        bundle.putInt("quantity", quantity)
                        bundle.putSerializable("product", product)
                        if (shared.getUser() == null){
//                            findNavController().navigate(R.id.action_homeFragment_to_loginFragment, bundle)
//                        }else{
                            findNavController().navigate(R.id.action_homeFragment_to_cartFragment, bundle)
                        }
                    }

                })
                binding.rv.adapter= adapter
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })










        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == lastSearch) return false

                api.search(query!!).enqueue(object : Callback<ProductData>{
                    override fun onResponse(
                        call: Call<ProductData>,
                        response: Response<ProductData>
                    ) {
                        val products = response.body()!!.products
                        changeProductsAdapter(products)
                    }

                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                        Log.d("TAG", "$t")
                    }

                })
                lastSearch = query

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
















        return binding.root
    }


    fun changeProductsAdapter(products: List<Product>) {
        binding.rv.adapter =
            RvAdapter( requireContext(),products, object : RvAdapter.myInterface {
                override fun onclick(product: Product) {
                    val bundle = Bundle()
                    bundle.putSerializable("product", product)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_productFragment,
                        bundle
                    )
                }

                override fun addtoCart(products: Product) {
                    val bundle = Bundle()
                    bundle.putInt("quantity", 1)
                    bundle.putSerializable("product", products)
                    findNavController().navigate(R.id.action_homeFragment_to_cartFragment, bundle)

                }
            }
                , object : MyBottomSheet.BottomSheetInterface{
                override fun onAdd(product: Product, quantity: Int) {
                    val shared = SharedPrefHelper.getInstance(requireContext())
                    val bundle = Bundle()
                    bundle.putInt("quantity", quantity)
                    bundle.putSerializable("product", product)
                    if (shared.getUser() == null){
//                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment, bundle)
                    }else{
                        findNavController().navigate(R.id.action_homeFragment_to_cartFragment, bundle)
                    }
                }

            })
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