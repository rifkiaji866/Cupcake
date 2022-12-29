/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [FlavorFragment] Method untuk mengizinkan user untuk memilih rasa cupcake pada pesanan nya
 */
class FlavorFragment : Fragment() {

    // Memanggil instance objek yang sesuai dengan tata letak fragment_flavor.xml
    // Properti ini bukan null/kososng di antara siklus data onCreateView() dan onDestroyView()
    // Disaat hirarki tampilan di lampirkan ke fragmen
    private var binding: FragmentFlavorBinding? = null

    // Gunakan delegasi properti Kotlin 'by activityViewModels()' dari class fragmen-ktx
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Menentukan fragment sebagai tampilan utama siklus penampil data
            lifecycleOwner = viewLifecycleOwner

            // Tetapkan model tampilan ke properti di kelas binding
            viewModel = sharedViewModel

            // Tetapkan fragmen
            flavorFragment = this@FlavorFragment
        }
    }

    /**
     * Arahkan ke layar berikutnya untuk memilih tanggal pengambilan.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * Method fragment lifecycle di panggil saat hirarki tampilan yang terkasit dengan fragment yang di hapus
     * lalu bersihkan objek yan tampil
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}