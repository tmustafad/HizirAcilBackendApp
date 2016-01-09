package com.hizir.acil.main.service;

import com.hizir.acil.main.dao.DonorDao;
import com.hizir.acil.main.model.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TTTDEMIRCI on 1/5/2016.
 */

@Service("donorService")
@Transactional
public class DonorServiceImpl implements DonorService {

    @Autowired
    private DonorDao dao;

    @Override
    public Donor findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void saveDonor(Donor donor) {
        dao.saveDonor(donor);
    }

    @Override
    public void updateDonor(Donor donor) {
        Donor entity=dao.findById(donor.getId());
        if(entity !=null){
            entity.setBloodType(donor.getBloodType());
            entity.setCreationDate(donor.getCreationDate());
            entity.setName(donor.getName());
            entity.setSurname(donor.getSurname());
        }
    }

    @Override
    public void deleteDonorById(int id) {
        dao.deleteDonor(dao.findById(id));
    }

    @Override
    public List<Donor> findAllDonors() {
        return dao.findAllDonors();
    }


}
