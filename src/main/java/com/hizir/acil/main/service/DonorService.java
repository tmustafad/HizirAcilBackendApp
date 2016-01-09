package com.hizir.acil.main.service;

import com.hizir.acil.main.model.Donor;

import java.util.List;

/**
 * Created by TTTDEMIRCI on 1/5/2016.
 */
public interface DonorService {

    Donor findById(int id);

    void saveDonor(Donor donor);

    void updateDonor(Donor donor);

    void deleteDonorById(int id);

    List<Donor> findAllDonors();
}
