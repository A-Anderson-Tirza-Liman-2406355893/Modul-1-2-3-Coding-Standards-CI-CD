# REFLECTION 1

Pada pengembangan aplikasi ini, saya telah mengimplementasikan dua fitur baru, yaitu edit product dan delete product, menggunakan framework Spring Boot dengan pendekatan arsitektur Model–View–Controller (MVC). Implementasi ini dilakukan dengan memperhatikan prinsip clean code serta praktik secure coding yang telah dipelajari dalam modul ini.

## Penerapan Prinsip Clean Code

Beberapa prinsip clean code yang telah diterapkan pada kode sumber aplikasi ini antara lain:

1. Separation of Concerns (SoC)
Aplikasi dibagi ke dalam beberapa lapisan dengan tanggung jawab yang jelas, yaitu:

- - Controller: menangani HTTP request dan response.

- - Service: berisi logika bisnis aplikasi.

- - Repository: bertanggung jawab terhadap pengelolaan data.

- - Model: merepresentasikan struktur data produk.

Pemisahan ini membuat kode lebih mudah dipahami, diuji, dan dikembangkan di masa depan.

2. Penamaan yang Jelas dan Konsisten
Penamaan kelas, method, dan variabel menggunakan nama yang deskriptif, seperti editProduct, findProductById, dan productListPage, sehingga maksud dari setiap bagian kode dapat dipahami tanpa perlu komentar tambahan.

3. Penghindaran Duplikasi Kode
Logika pengambilan dan manipulasi data produk dipusatkan pada layer service dan repository, sehingga tidak terjadi pengulangan kode di controller.

4. Struktur Method yang Sederhana
Setiap method dibuat dengan satu tujuan yang jelas (single responsibility), misalnya method editProductPost hanya bertanggung jawab untuk memproses perubahan data produk.

## Penerapan Secure Coding Practices

Dalam pengembangan fitur edit dan delete, beberapa praktik keamanan juga telah diterapkan, antara lain:

1. Penggunaan Path Variable untuk Identifikasi Data
Penghapusan dan pengeditan produk dilakukan menggunakan productId sebagai identifier, sehingga operasi hanya dilakukan pada data yang spesifik.

2. Penghindaran Manipulasi ID oleh User
Pada halaman edit, productId dikirim melalui input tersembunyi (hidden field), sehingga tidak dapat diubah secara langsung oleh pengguna melalui antarmuka.

3. Validasi Alur Aplikasi
Setiap request edit dan delete dipetakan secara eksplisit melalui controller (@GetMapping dan @PostMapping), sehingga mencegah akses endpoint yang tidak terdefinisi.

## Evaluasi dan Perbaikan Kode

Selama proses pengembangan, ditemukan beberapa hal yang dapat diperbaiki, antara lain:

- Product ID Tidak Diinisialisasi Saat Create

Ini merupakan kesalahan saya pada awalnya implementasi. Awalnya, productId tidak dihasilkan secara otomatis saat pembuatan produk. Hal ini menyebabkan error ketika melakukan edit karena URL membutuhkan ID yang valid.
Perbaikan dilakukan dengan menghasilkan productId secara otomatis menggunakan UUID, sehingga setiap produk memiliki identifier yang unik.

- Belum Menggunakan Validasi Input

Saat ini aplikasi belum menggunakan validasi seperti @NotNull atau @Min pada atribut produk. Ke depannya, validasi ini dapat ditambahkan untuk meningkatkan keandalan aplikasi.

- Penyimpanan Data Masih In-Memory

Repository masih menggunakan struktur data List, sehingga data akan hilang saat aplikasi dihentikan. Untuk pengembangan selanjutnya, aplikasi dapat diintegrasikan dengan database menggunakan Spring Data JPA.
