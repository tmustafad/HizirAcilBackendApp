package com.hizir.acil.main.controller;


import com.hizir.acil.main.model.Donor;
import com.hizir.acil.main.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


/**
 * Created by TTTDEMIRCI on 12/29/2015.
 */


@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    DonorService service;

    @Autowired
    MessageSource messageSource;

/*
     * This method will list all existing Donors in for JSP .
     */

    @RequestMapping(value = { "/", "/listAllDonors" }, method = RequestMethod.GET)
    public String listDonors(ModelMap model) {

        List<Donor> donors = service.findAllDonors();
        model.addAttribute("donors", donors);
        return "alldonors";
    }
    /*
     * This method will list all existing Donors in json format.
     */
    @RequestMapping(value = {  "/listjson" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Donor>> listDonors() {
        List<Donor> donors = service.findAllDonors();
        if (donors.isEmpty()) {
            return new ResponseEntity<List<Donor>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Donor>>(donors, HttpStatus.OK);
    }

    /*
     * This method will provide the medium to add a new donor.
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String newDonor(ModelMap model) {
        Donor donor = new Donor();
        model.addAttribute("donor", donor);
        model.addAttribute("edit", false);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving donor in database. It also validates the user input
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public String saveDonor(@Valid Donor donor, BindingResult result,
                               ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

        /*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
         * and applying it on field [ssn] of Model class [Employee].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
      /*  if(!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())){
            FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
            result.addError(ssnError);
            return "registration";
        }*/

        service.saveDonor(donor);

        model.addAttribute("success", "Donor " + donor.getName() + " registered successfully");
        return "success";
    }


    /*
     * This method will provide the medium to update an existing Donor.
     */
    @RequestMapping(value = { "/edit-{id}-donor" }, method = RequestMethod.GET)
    public String editDonor(@PathVariable int id, ModelMap model) {
        Donor donor= service.findById(id);
        model.addAttribute("donor", donor);
        model.addAttribute("edit", true);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating donor in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{id}-donor" }, method = RequestMethod.POST)
    public String updateDonor(@Valid Donor donor, BindingResult result,
                                 ModelMap model, @PathVariable int id) {

        if (result.hasErrors()) {
            return "registration";
        }

//        if(!service.isEmployeeSsnUnique(employee.getId(), employee.getSsn())){
//            FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
//            result.addError(ssnError);
//            return "registration";
//        }

        service.updateDonor(donor);

        model.addAttribute("success", "Donor " + donor.getName()  + " updated successfully");
        return "success";
    }


    /*
     * This method will delete a donor  by it's id value.
     */
    @RequestMapping(value = { "/delete-{id}-donor" }, method = RequestMethod.GET)
    public String deleteDonorById(@PathVariable int id) {
        service.deleteDonorById(id);
        return "redirect:/listAllDonors";
    }

}
