package lk.ijse.dep.app.dao;

import lk.ijse.dep.app.db.DBConnection;
import lk.ijse.dep.app.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {

    private CrudUtil(){}

    public static <T> T execute(String sql, Object... params) throws SQLException {
      Connection connection = DBConnection.getConnection();

//        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//             Session session = sessionFactory.openSession()) {
//
         PreparedStatement pstm = connection.prepareStatement(sql);
//        //    Query pstm = session.createQuery(sql);
            int parametersCount = getParametersCount(sql);

            if (params.length != parametersCount) {
                throw new RuntimeException("Parameters count mismatched error");
            }

            for (int i = 0; i < parametersCount; i++) {
                pstm.setObject(i + 1, params[i]);
            }

            return sql.trim().startsWith("SELECT") ? (T) pstm.executeQuery() : (T) (Integer) pstm.executeUpdate();

    }

    private static int getParametersCount(String sql) {
        return sql.concat(" ").split("[?]").length - 1;
    }

}
