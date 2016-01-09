package com.hizir.acil.main.dao;

import com.hizir.acil.main.model.Donor;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by TTTDEMIRCI on 1/8/2016.
 */
public class DonorDaoImpl extends  AbstractDao<Integer,Donor> implements DonorDao {

    @Override
    public Donor findById(int id) {
        return getByKey(id);
    }

    @Override
    public void saveDonor(Donor donor) {
        persist(donor);
    }


    @Override
    public void deleteDonor(Donor donor) {
        delete(donor);
    }

    @Override
    public List<Donor> findAllDonors() {
        Criteria criteria=createEntityCriteria();
        return (List<Donor>)criteria.list();

    }
}
