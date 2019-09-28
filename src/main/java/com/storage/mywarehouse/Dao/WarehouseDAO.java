package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import static org.hibernate.criterion.Restrictions.and;
import static org.hibernate.criterion.Restrictions.eq;

public class WarehouseDAO {
    public static Warehouse findByName(String name) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Warehouse existingWarehouse = (Warehouse) session.createCriteria(Warehouse.class)
                .add(and(
                        eq("name", name)
                )).uniqueResult();
        transaction.commit();
        session.close();
        return existingWarehouse;
    }

    public static Warehouse saveWithName(String name) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int warehouseId;
        Warehouse withHighestId = (Warehouse) session.createCriteria(Warehouse.class)
                .addOrder(Order.desc("warehouseId"))
                .setMaxResults(1)
                .uniqueResult();
        if (withHighestId == null) {
            warehouseId = 0;
        } else {
            warehouseId = withHighestId.getWarehouseId() + 1;
        }
        Warehouse w = new Warehouse(warehouseId, name);
        session.save(w);
        transaction.commit();
        session.close();
        return w;
    }
}
