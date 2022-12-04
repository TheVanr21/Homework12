package ru.netology.repository;

import org.junit.jupiter.api.*;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.exception.NotFoundException;

public class ProductRepositoryTest {

    Product product1 = new Book(1, "The Fellowship of the Ring", 700, "J. R. R. Tolkien");
    Product product2 = new Book(2, "The Two Towers", 750, "J. R. R. Tolkien");
    Product product3 = new Book(3, "The Return of the King", 800, "J. R. R. Tolkien");
    Product product4 = new Smartphone(4, "S22 Ultra", 100000, "Samsung");
    Product product5 = new Smartphone(5, "Pixel 7", 50000, "Google");

    ProductRepository repository = new ProductRepository();

    @Test
    public void shouldSave() {
        try {
            repository.save(product1);
            repository.save(product4);
        } catch (AlreadyExistsException e) {
            //e.printStackTrace();
        }

        Product[] expected = {product1, product4};
        Product[] actual = repository.getAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotSaveDuplicates() {
        try {
            repository.save(product1);
            repository.save(product1);
        } catch (AlreadyExistsException e) {
            //e.printStackTrace();
        }

        Product[] expected = {product1};
        Product[] actual = repository.getAll();

        Assertions.assertArrayEquals(expected, actual);
        Assertions.assertThrows(AlreadyExistsException.class, () -> {
            repository.save(product1);
            repository.save(product1);
        });
    }

    @Nested
    public class WithInitiallyAddedProducts {

        @BeforeEach
        public void init() {
            try {
                repository.save(product1);
                repository.save(product2);
                repository.save(product3);
                repository.save(product4);
                repository.save(product5);
            } catch (AlreadyExistsException e) {
                //e.printStackTrace();
            }
        }

        @Test
        public void shouldFindById() {
            Product expected = product1;
            Product actual = repository.findById(1);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void shouldNotFindByWrongId() {
            Product expected = null;
            Product actual = repository.findById(6);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void shouldRemoveById() {
            try {
                repository.removeById(3);
            } catch (NotFoundException e) {
                //e.printStackTrace();
            }

            Product[] expected = {product1, product2, product4, product5};
            Product[] actual = repository.getAll();

            Assertions.assertArrayEquals(expected, actual);
        }

        @Test
        public void shouldNotRemoveById() {
            try {
                repository.removeById(6);
            } catch (NotFoundException e) {
                //e.printStackTrace();
            }

            Product[] expected = {product1, product2, product3, product4, product5};
            Product[] actual = repository.getAll();

            Assertions.assertArrayEquals(expected, actual);
            Assertions.assertThrows(NotFoundException.class, () -> {
                repository.removeById(6);
            });
        }
    }
}
