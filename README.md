Deployment Link: https://successful-danica-adpro2026-f827b48d.koyeb.app/product/list

# Modul 1

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

# REFLECTION 2

## Refleksi Setelah Menulis Unit Test

Setelah menulis unit test, saya merasa lebih percaya diri terhadap stabilitas dan kebenaran logika program yang dikembangkan. Unit test membantu saya memahami alur eksekusi kode secara lebih mendalam, terutama dalam memastikan bahwa setiap method bekerja sesuai dengan tanggung jawabnya. Selain itu, unit test juga mempermudah proses refactoring karena kesalahan dapat terdeteksi lebih awal.

Jumlah unit test dalam satu kelas tidak dapat ditentukan secara pasti, karena bergantung pada kompleksitas dan tanggung jawab kelas tersebut. Namun, prinsip yang sebaiknya diikuti adalah **setiap perilaku (behavior) penting dari sebuah method harus diuji**. Idealnya, satu test case memverifikasi satu skenario atau satu kondisi spesifik agar mudah dipahami dan dipelihara.

Untuk memastikan bahwa unit test sudah cukup dalam memverifikasi program, salah satu metrik yang dapat digunakan adalah **code coverage**. Code coverage menunjukkan seberapa besar bagian dari kode sumber yang telah dieksekusi oleh test. Meskipun memiliki code coverage 100% terdengar ideal, hal tersebut **tidak menjamin bahwa kode bebas dari bug atau error**. Code coverage hanya mengukur apakah baris kode dijalankan, bukan apakah seluruh kemungkinan logika, edge case, atau kesalahan desain telah diuji dengan benar. Oleh karena itu, kualitas test case jauh lebih penting daripada sekadar angka coverage.

## Refleksi terhadap Pembuatan Functional Test Tambahan

Jika setelah membuat `CreateProductFunctionalTest.java` saya diminta untuk membuat functional test baru untuk memverifikasi jumlah item dalam product list, lalu saya membuat kelas baru dengan setup dan variabel yang hampir sama, maka dari sisi clean code terdapat beberapa potensi masalah.

### Potensi Masalah Clean Code

1. **Duplikasi Kode (Code Duplication)**  
   Setup seperti inisialisasi WebDriver, konfigurasi URL dasar, dan proses setup/teardown kemungkinan akan ditulis ulang di kelas functional test yang baru. Duplikasi ini melanggar prinsip *DRY (Don't Repeat Yourself)* dan dapat menyulitkan pemeliharaan kode.

2. **Tight Coupling antar Test Suite**  
   Jika setiap functional test mengatur sendiri konfigurasi lingkungan (misalnya URL, timeout, atau browser), maka perubahan kecil pada konfigurasi akan membutuhkan perubahan di banyak kelas test.

3. **Menurunnya Readability dan Maintainability**  
   Kode yang berulang dan panjang membuat test suite menjadi lebih sulit dibaca, terutama ketika jumlah functional test bertambah. Hal ini dapat menurunkan kualitas kode secara keseluruhan.

### Saran Perbaikan

Untuk meningkatkan kebersihan dan kualitas kode, beberapa perbaikan yang dapat dilakukan adalah:

- **Membuat Base Functional Test Class**  
  Setup umum seperti inisialisasi WebDriver, konfigurasi browser, dan teardown dapat dipindahkan ke satu kelas abstrak (misalnya `BaseFunctionalTest`) yang kemudian diwarisi oleh semua functional test suite.

- **Menggunakan Utility atau Helper Class**  
  Interaksi yang sering dilakukan, seperti membuka halaman tertentu atau mengisi form, dapat diekstrak ke dalam helper method agar test case lebih ringkas dan fokus pada tujuan pengujian.

- **Menjaga Satu Tanggung Jawab per Test Class**  
  Setiap functional test class sebaiknya hanya menguji satu fitur utama, misalnya create product atau verifikasi product list, sehingga test tetap jelas dan terstruktur.

Dengan menerapkan perbaikan tersebut, functional test suite akan menjadi lebih bersih, mudah dikembangkan, dan tetap selaras dengan prinsip clean code meskipun jumlah test semakin bertambah.

# Modul 2

# Reflection

Pada bagian ini, saya mengimplementasi Continuous Integration dan Continuous Deployment (CI/CD) yang telah dilakukan menggunakan GitHub Actions dan platform PaaS (Koyeb).

## 1. Code Quality / Security Issues dan Strategi Penyelesaian
Selama pengerjaan exercise ini, salah satu isu kualitas dan keamanan yang saya perbaiki adalah Pinned Dependencies pada workflow GitHub Actions.

- Isunya adalah tag yang digunakan pada action GitHub dan image Docker sebelumnya bersifat mutable (dapat berubah dari waktu ke waktu, misalnya menggunakan tag @v4 atau latest). Hal ini menimbulkan celah keamanan tingkat menengah/tinggi, karena jika versi action tersebut diubah oleh pihak ketiga (baik karena update biasa atau disusupi kode berbahaya), alur eksekusi CI/CD aplikasi bisa rusak atau terkompromi tanpa disadari.

- Strategi penyelesaian yang saya lakukan adalah dengan mengikuti rekomendasi Security Hardening dari GitHub dan The Open Source Security Foundation (OpenSSF). Saya memodifikasi file workflow dengan mengganti tag versi mutable menjadi full-length commit SHA yang spesifik dan immutable (tidak dapat diubah). Proses ini dibantu melalui automasi Pull Request dari StepSecurity Bot. Dengan mengunci (pinning) dependensi ke commit SHA tertentu, saya memastikan bahwa pipeline selalu menjalankan kode pihak ketiga yang persis sama dan sudah diverifikasi keamanannya pada setiap eksekusi.

## 2. Evaluasi Implementasi CI/CD
Menurut saya, implementasi workflow GitHub dan konfigurasi repositori saat ini sudah memenuhi definisi dan standar dari Continuous Integration (CI) maupun Continuous Deployment (CD). Alasannya:

- Pertama, dari sisi Continuous Integration, workflow telah dikonfigurasi agar setiap kali ada kode yang di-push atau diajukan melalui Pull Request. Kemudian, GitHub Actions secara otomatis akan menjalankan proses build, mengeksekusi seluruh test suites (unit dan functional test), serta melakukan analisis kualitas dan keamanan kode (lewat OSSF Scorecard). Hal ini memastikan bahwa setiap perubahan kode divalidasi kebenarannya secara terus-menerus sebelum digabungkan ke branch utama.

- Kedua, dari sisi Continuous Deployment, repositori GitHub ini telah diintegrasikan secara langsung dengan Platform as a Service (PaaS) yaitu Koyeb menggunakan pendekatan Pull-based deployment (atau Auto-deploy). Begitu kode berhasil melewati tahap CI dan di-merge ke branch main, PaaS akan secara otomatis mendeteksi perubahan tersebut, menarik kode atau image terbaru, lalu men-deploy aplikasi ke environment produksi tanpa memerlukan intervensi manual sama sekali. Alur ini menciptakan siklus rilis perangkat lunak yang cepat, aman, dan konsisten.
