package ru.netology.repository;

import ru.netology.domain.Product;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.exception.NotFoundException;

public class ProductRepository {
    Product[] products = new Product[0];

    public Product[] getAll() {
        return products;
    }

    public void removeById(int id) {
        if (findById(id) != null) {
            Product[] tmp = new Product[products.length - 1];
            int indexForCopy = 0;
            for (Product product : products) {
                if (product.getId() != id) {
                    tmp[indexForCopy] = product;
                    indexForCopy++;
                }
            }
            products = tmp;
        } else {
            throw new NotFoundException("Продукт с ID:" + id + " не найден");
        }
    }

    public Product findById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void save(Product newProduct) {
        if (findById(newProduct.getId()) == null) {
            Product[] tmp = new Product[products.length + 1];
            System.arraycopy(products, 0, tmp, 0, products.length);
            tmp[tmp.length - 1] = newProduct;
            products = tmp;
        } else {
            throw new AlreadyExistsException("Продукт с ID:" + newProduct.getId() + " уже есть в репозитории");
        }
    }
}
