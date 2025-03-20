package my.finance.repository;

import jakarta.persistence.TypedQuery;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface  UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}


