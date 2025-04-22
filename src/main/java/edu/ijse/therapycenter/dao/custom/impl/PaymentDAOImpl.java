package edu.ijse.therapycenter.dao.custom.impl;

import edu.ijse.therapycenter.config.FactoryConfiguration;
import edu.ijse.therapycenter.dao.custom.PaymentDAO;
import edu.ijse.therapycenter.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Payment payment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factoryConfiguration.getSession();
            transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Payment payment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factoryConfiguration.getSession();
            transaction = session.beginTransaction();
            session.update(payment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factoryConfiguration.getSession();
            transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, pk);
            if (payment != null) {
                session.delete(payment);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Payment> getAll() {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("FROM Payment", Payment.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Optional<Payment> findByPK(String pk) {
        try (Session session = factoryConfiguration.getSession()) {
            Payment payment = session.get(Payment.class, pk);
            return Optional.ofNullable(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getLastPK() {
        try (Session session = factoryConfiguration.getSession()) {
            Long lastPk = session
                    .createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC", Long.class)
                    .setMaxResults(1)
                    .uniqueResult();

            Long newPk = (lastPk != null) ? lastPk + 1 : 1;
            return Optional.of(String.valueOf(newPk));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        try (Session session = factoryConfiguration.getSession()) {
            Long count = session.createQuery("SELECT COUNT(p) FROM Payment p WHERE p.id = :id", Long.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error checking payment existence", e);
        }
    }
}