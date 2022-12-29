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
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel

/**
 * kelas StartFragment yang digunakan untuk melakukan order atau pemesanan
 */
class StartFragment : Fragment() {

    // Mengikat atau menghubungkan FragmentStart dengan StartFragment
    private var binding: FragmentStartBinding? = null

    // Membagi ViewModel agar bisa digunkan di fragment ini
    private val sharedViewModel: OrderViewModel by activityViewModels()

    /**
     * Membangun tampilan dari fragment yang terikat
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    /**
     * Menampilkan tampilan yang telah terbangun dari fragment yang terikat
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this
    }

    /**
     * Mengatur jumlah (ukuran) yang akan di beli
     */
    fun orderCupcake(quantity: Int) {
        // Memperbarui view model untuk jumlah yang di beli
        sharedViewModel.setQuantity(quantity)

        // Memilih rasa vanila sebagai pilihan utama jika data masih kosong
        if (sharedViewModel.hasNoFlavorSet()) {
            sharedViewModel.setFlavor(getString(R.string.vanilla))
        }

        // Navigasi untuk lanjut ke halaman memilih rasa
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    /**
     * Menghapus atau mengganti tampilan agar tampilan selanjutnya atau sebelumnya dapat tampil
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}