package com.example.pendaftaranseminar

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RingkasanActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAMA     = "EXTRA_NAMA"
        const val EXTRA_EMAIL    = "EXTRA_EMAIL"
        const val EXTRA_WA       = "EXTRA_WA"
        const val EXTRA_KATEGORI = "EXTRA_KATEGORI"
        const val EXTRA_SESI     = "EXTRA_SESI"
        const val EXTRA_FAKULTAS = "EXTRA_FAKULTAS"
        const val EXTRA_METODE   = "EXTRA_METODE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringkasan)

        // 1) ambil Intent yang membawa kita ke layar ini, lalu baca Extra-nya.
        //    Operator ?: dipakai sebagai nilai pengganti bila data tidak ditemukan.
        val nama     = intent.getStringExtra(EXTRA_NAMA)     ?: "-"
        val email    = intent.getStringExtra(EXTRA_EMAIL)    ?: "-"
        val wa       = intent.getStringExtra(EXTRA_WA)       ?: "-"
        val kategori = intent.getStringExtra(EXTRA_KATEGORI) ?: "-"
        val sesi     = intent.getStringExtra(EXTRA_SESI)     ?: "-"
        val fakultas = intent.getStringExtra(EXTRA_FAKULTAS) ?: "-"
        val metode   = intent.getStringExtra(EXTRA_METODE)   ?: "Belum dipilih"

        // 2) tampilkan ke layar
        findViewById<TextView>(R.id.tvNama).text = nama
        findViewById<TextView>(R.id.tvDetail).text = buildString {
            appendLine("Email        : $email")
            appendLine("WhatsApp     : $wa")
            appendLine("Kategori     : $kategori")
            appendLine("Sesi         : $sesi")
            appendLine("Fakultas     : $fakultas")
            append("Pembayaran   : $metode")
        }

        // 3) tombol kembali menutup layar ini, form sebelumnya akan muncul kembali
        findViewById<Button>(R.id.btnKembali).setOnClickListener {
            finish()
        }

        // Tombol Bagikan (implicit intent)
        findViewById<Button>(R.id.btnBagikan).setOnClickListener {
            // 1) susun teks yang akan dibagikan
            val ringkasan = buildString {
                appendLine("Pendaftaran Seminar")
                appendLine("Nama     : $nama")
                appendLine("Email    : $email")
                appendLine("Kategori : $kategori")
                appendLine("Sesi     : $sesi")
                append("Fakultas : $fakultas")
            }

            // 2) implicit intent: hanya menyebut AKSI, bukan Activity tujuannya
            val bagikan = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, ringkasan)
            }

            // 3) createChooser menampilkan pilihan aplikasi; bungkus try-catch
            //    untuk berjaga bila perangkat tidak punya aplikasi pendukung
            try {
                startActivity(Intent.createChooser(bagikan, "Bagikan ringkasan lewat"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi untuk berbagi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}