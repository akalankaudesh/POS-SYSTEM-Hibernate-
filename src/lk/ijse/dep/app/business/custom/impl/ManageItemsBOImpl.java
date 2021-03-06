package lk.ijse.dep.app.business.custom.impl;

import lk.ijse.dep.app.business.Converter;
import lk.ijse.dep.app.business.custom.ManageItemsBO;
import lk.ijse.dep.app.dao.DAOFactory;
import lk.ijse.dep.app.dao.custom.ItemDAO;
import lk.ijse.dep.app.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public class ManageItemsBOImpl implements ManageItemsBO {

    private ItemDAO itemDAO;

    public ManageItemsBOImpl(){
        itemDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
    }

    public List<ItemDTO> getItems() throws SQLException{
        return itemDAO.findAll().map(Converter::<ItemDTO>getDTOList).get();
    }

    public boolean createItem(ItemDTO dto) throws SQLException {
        return itemDAO.save(Converter.getEntity(dto));
    }

    public boolean updateItem(ItemDTO dto) throws SQLException{
        return itemDAO.update(Converter.getEntity(dto));
    }

    public boolean deleteItem(String code) throws SQLException {
        return itemDAO.delete(code);

    }

    public ItemDTO findItem(String itemCode) throws SQLException {
        return itemDAO.find(itemCode).map(Converter::<ItemDTO>getDTO).orElse(null);
    }
}
