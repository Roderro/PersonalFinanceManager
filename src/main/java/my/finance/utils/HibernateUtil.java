package my.finance.utils;

import my.finance.models.BudgetCategory;
import my.finance.models.AppTransaction;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") // Указываем путь к файлу конфигурации
                    .build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClasses(User.class, Wallet.class, AppTransaction.class, BudgetCategory.class);
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}