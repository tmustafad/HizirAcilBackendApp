package com.hizir.acil.main.dao;

import com.hizir.acil.main.model.Donor;

import java.util.List;

/**
 * Created by TTTDEMIRCI on 1/8/2016.
 */
public interface DonorDao {

    Donor findById(int id);
    void saveDonor(Donor donor);
    void deleteDonor(Donor donor);
    List<Donor> findAllDonors();

}
