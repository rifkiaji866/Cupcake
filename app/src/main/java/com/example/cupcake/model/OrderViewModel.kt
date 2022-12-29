
package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Variabel harga untuk satu cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Variabel untuk tambahan harga untuk order dan ambil di hari yang sama */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel] Untuk menyimpan informasi tentang pesanan cupcake dalam jumlah dan rasa
 * tanggal serta tanggal pengambilan lalu menghitung harga total berdasarkan detail pesanan .
 */
class OrderViewModel : ViewModel() {

    // Program ini untuk mendeklarasi jumlah pesanan cupcake
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    // Program ini untuk mendeklarasi rasa cupcake
    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    // Program untuk memasukkan tanggal
    val dateOptions: List<String> = getPickupOptions()

    // Program untuk mendeklarasi tanggal penjemputan
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    //Program untuk menentukan harga pesanan yang jauh
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        // Format harga ke dalam mata uang lokal dan dikembalikan sebagai LiveData<String>
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        // Untuk mereset pesanan
        resetOrder()
    }

    /**
     * Program menentukan jumlah cupcake untuk setiap pesanan
     *
     * @param numberCupcakes sebagai method  tiap pesanan
     */
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    /**
     * Program untuk menentukan rasa cupcake di tiap pesanan dan hanya 1 rasa yang dapat di pilih di tiap pesanan.
     *
     * @param desiredFlavor sebagai method untuk mengubah menjadi string
     */
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    /**
     * Program untuk menetapkan tanggal pengambilan pesanan ini
     *
     * @param pickupDate sebagai method untuk mengubah tanggal pengambilan menjadi string
     */
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    /**
     * Method untuk mengembalikan nilai benar/ada jika rasa cupcake belum dipilih. dan mengembalikan nilai salah/tidak jika tidak memilih
     */
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    /**
     * Method untuk me-reset form dengan menggunakan nilai default
     */
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    /**
     * Program untuk memperbarui harga berdasarkan pesanan
     */
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    /**
     * Program untuk mengembalikan daftar opsi tanggal yang di mulai dengan tanggal saat ini dan tanggal berikutnya
     */
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}