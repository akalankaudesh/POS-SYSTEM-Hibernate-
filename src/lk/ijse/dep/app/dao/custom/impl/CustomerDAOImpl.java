package lk.ijse.dep.app.dao.custom.impl;

import lk.ijse.dep.app.dao.CrudUtil;
import lk.ijse.dep.app.dao.custom.CustomerDAO;
import lk.ijse.dep.app.db.HibernateUtil;
import lk.ijse.dep.app.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {



    @Override
    public Optional<Customer> find(String customerId) throws SQLException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session = sessionFactory.openSession();
            session.beginTransaction();
            Customer customer = session.createQuery("SELECT c FROM Customer c WHERE c.id=?", Customer.class).getSingleResult();
           // ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE id=?", customerId);
        session.getTransaction().commit();
        Customer c = null;
//        if (rst.next()) {
//            c= new Customer(rst.getString("id"),
//                    rst.getString("name"),
//                    rst.getString("address"));
//        }

            c=new Customer(customer.getId(),customer.getName(),customer.getAddress());
        return Optional.ofNullable(c);

    }

    public Optional<List<Customer>> findAll() throws SQLException {
        System.out.println("Asdsad");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session = sessionFactory.openSession();
             String sql="SELECT * FROM Customer";
            session.beginTransaction();
            List<Customer> customerList = session.createNativeQuery(sql, Customer.class).list();
        session.getTransaction().commit();
        session.close();

            return Optional.ofNullable(customerList);

    }

    public boolean save(Customer customer) throws SQLException {
        boolean result= false;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(customer);
        session.getTransaction().commit();
            result=true;
            return result;


            //  return CrudUtil.<Integer>execute("INSERT INTO Customer VALUES (?,?,?)",
                //    customer.getId(), customer.getName(), customer.getAddress()) > 0;

//            session.createQuery("INSERT lk.ijse.dep.app.entity.Customer"+ "c VALUES (c.id=?1,c.name?2,c.address=?3")
//                    .setParameter(1,customer.getId())
//                    .setParameter(2,customer.getName())
//                    .setParameter(3,customer.getAddress())


    }

    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.<Integer>execute("UPDATE Customer SET name=?,address=? WHERE id=?",
                customer.getName(), customer.getAddress(), customer.getId()) > 0;
    }

    public boolean delete(String customerId) throws SQLException {
        return CrudUtil.<Integer>execute("DELETE FROM Customer WHERE id=?", customerId)> 0;
    }

}
