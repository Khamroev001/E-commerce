package com.example.e_commerce.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.e_commerce.R
import com.example.e_commerce.adapter.CartProductsAdapter
import com.example.e_commerce.databinding.FragmentCartBinding
import com.example.e_commerce.model.CartData
import com.example.e_commerce.model.Product
import com.example.e_commerce.model.ProductX
import com.example.e_commerce.model.User
import com.example.e_commerce.networking.APIClient
import com.example.e_commerce.networking.APIService
import com.example.e_commerce.utils.SharedPrefHelper
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class CartFragment : Fragment() {
    var product: Product? = null
    private lateinit var binding: FragmentCartBinding
    private var quantity: Int = 0
    private val api: APIService = APIClient.getInstance().create(APIService::class.java)
    private lateinit var user: User
    private var didLogin: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments?.getSerializable("product") != null) {
            product = arguments?.getSerializable("product") as Product
            quantity = arguments?.getInt("quantity", 0)!!
            didLogin = arguments?.getBoolean("didLogin", false)!!
        }
        val shared = SharedPrefHelper.getInstance(requireContext())
        user = shared.getUser()!!

        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.cartRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartBackFab.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        setCart()
        setuser()
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (didLogin) {
                    findNavController().popBackStack()
                }
                findNavController().popBackStack()
            }

        })
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setuser() {
        binding.cartUserImage.load(user.image)
        binding.cartName.text = user.firstName + " " + user.lastName
        binding.cartUsername.text = "@${user.username}"
        binding.cartEmail.text = user.email
    }

    private fun setCart() {
        api.getCartsOfUser(user.id).enqueue(object : retrofit2.Callback<CartData> {
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                if (response.isSuccessful) {
                    val products = response.body()!!.carts[0].products.toMutableList()
                    if (product != null) {
                        val productX = ProductX(
                            0.0,
                            0,
                            product!!.id,
                            product!!.price,
                            quantity,
                            product!!.title,
                            product!!.price * quantity
                        )
                        products.add(0, productX)
                    }
                    binding.cartRecycler.adapter = CartProductsAdapter(products)
                }
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
    }
}