package my.finance.repository;

import java.util.Optional;

public interface Repository<T> {


    Optional<T> getByID(int key);

    void add(T item);

    void delete(T item);

    Optional<T> update(T item);

    void deleteByID(int key);
}
