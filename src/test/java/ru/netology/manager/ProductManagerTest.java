package ru.netology.manager;

import org.junit.jupiter.api.*;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

public class ProductManagerTest {

    Product product1 = new Book(1, "The Fellowship of the Ring", 700, "J. R. R. Tolkien");
    Product product2 = new Book(2, "The Two Towers", 750, "J. R. R. Tolkien");
    Product product3 = new Book(3, "The Return of the King", 800, "J. R. R. Tolkien");
    Product product4 = new Smartphone(4, "S22 Ultra", 100000, "Samsung");
    Product product5 = new Smartphone(5, "Pixel 7", 50000, "Google");

    ProductRepository repository = new ProductRepository();
    ProductManager manager = new ProductManager(repository);

    @Test
    public void shouldAdd() {
        manager.add(product1);
        manager.add(product4);

        Product[] expected = {product1, product4};
        Product[] actual = repository.getAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Nested
    public class WithInitiallyAddedProducts {

        @BeforeEach
        public void init() {
            manager.add(product1);
            manager.add(product2);
            manager.add(product3);
            manager.add(product4);
            manager.add(product5);
        }

        @Test
        public void shouldSearchByBookName() {
            Product[] expected = {product1, product3};
            Product[] actual = manager.searchBy("the");

            Assertions.assertArrayEquals(expected, actual);
        }

        @Test
        public void shouldSearchByBookAuthor() {
            Product[] expected = {product1, product2, product3};
            Product[] actual = manager.searchBy("Tolkien");

            Assertions.assertArrayEquals(expected, actual);
        }

        @Test
        public void shouldSearchBySmartphoneName() {
            Product[] expected = {product5};
            Product[] actual = manager.searchBy("Pixel 7");

            Assertions.assertArrayEquals(expected, actual);
        }

        @Test
        public void shouldSearchBySmartphoneBrand() {
            Product[] expected = {product4};
            Product[] actual = manager.searchBy("Samsung");

            Assertions.assertArrayEquals(expected, actual);
        }

        @Test
        public void shouldNotSearchByWrongText() {
            Product[] expected = {};
            Product[] actual = manager.searchBy("something");

            Assertions.assertArrayEquals(expected, actual);
        }
    }
}
