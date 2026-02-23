package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp(){
    }
    @Test
    void testCreateAndFind(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct_Success() {
        Product product = new Product();
        product.setProductName("Sampo Lama");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);
        String productId = savedProduct.getProductId();

        Product editedProduct = new Product();
        editedProduct.setProductId(productId);
        editedProduct.setProductName("Sampo Baru");
        editedProduct.setProductQuantity(20);

        productRepository.edit(editedProduct);

        Product result = productRepository.findProductById(productId);
        assertNotNull(result);
        assertEquals("Sampo Baru", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditProduct_Failed_ProductNotFound() {
        Product editedProduct = new Product();
        editedProduct.setProductId("ID-TIDAK-ADA");
        editedProduct.setProductName("Produk Palsu");
        editedProduct.setProductQuantity(999);

        productRepository.edit(editedProduct);

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProduct_Success() {
        Product product = new Product();
        product.setProductName("Produk Dihapus");
        product.setProductQuantity(5);

        Product savedProduct = productRepository.create(product);
        String productId = savedProduct.getProductId();

        productRepository.delete(productId);

        Product result = productRepository.findProductById(productId);
        assertNull(result);
    }

    @Test
    void testDeleteProduct_Failed_ProductNotFound() {
        productRepository.delete("ID-TIDAK-PERNAH-ADA");

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindProductById_IfProductDoesNotExistButListIsNotEmpty() {
        Product product = new Product();
        productRepository.create(product); // ID di-generate oleh repository

        // Cari ID yang jelas-jelas tidak ada
        Product result = productRepository.findProductById("id-tidak-ada");

        // Loop akan berjalan, tapi kondisi 'if' akan bernilai false sehingga me-return null
        assertNull(result);
    }

    @Test
    void testFindProductById_IfProductIsMoreThanOne() {
        // Tambahkan produk pertama
        Product product1 = new Product();
        Product savedProduct1 = productRepository.create(product1);

        // Tambahkan produk kedua
        Product product2 = new Product();
        Product savedProduct2 = productRepository.create(product2);

        // Cari produk KEDUA menggunakan ID yang di-generate repository.
        Product result = productRepository.findProductById(savedProduct2.getProductId());

        assertEquals(savedProduct2.getProductId(), result.getProductId());
    }

    @Test
    void testEditProduct_IfProductDoesNotExistButListIsNotEmpty() {
        // Buat satu produk agar list tidak kosong
        Product product1 = new Product();
        product1.setProductName("Sampo Asli");
        product1.setProductQuantity(100);
        Product savedProduct1 = productRepository.create(product1);

        // Coba edit produk dengan ID yang berbeda (ID tidak ada di list)
        Product editedProduct = new Product();
        editedProduct.setProductId("id-tidak-ada");
        editedProduct.setProductName("Sampo Palsu");
        editedProduct.setProductQuantity(20);

        productRepository.edit(editedProduct);

        // Loop berjalan, tapi if false. Pastikan produk asli tidak ikut berubah
        // Cari menggunakan ID yang di-generate repository
        Product result = productRepository.findProductById(savedProduct1.getProductId());

        assertEquals("Sampo Asli", result.getProductName());
        assertEquals(100, result.getProductQuantity());
    }
}