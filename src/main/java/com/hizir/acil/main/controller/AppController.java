package com.hizir.acil.main.controller;


import com.hizir.acil.main.model.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    EmployeeService service;

    @Autowired
    MessageSource messageSource;

    /*
     * This method will list all existing Donors.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listDonors(ModelMap model) {

        List<Donor> donors = service.findAllDonors();
        model.addAttribute("donors", donors);
        return "alldonors";
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
    @RequestMapping(value = { "/edit-{id}-employee" }, method = RequestMethod.GET)
    public String editDonor(@PathVariable Long id, ModelMap model) {
        Donor donor= service.findDonorById(id);
        model.addAttribute("donor", donor);
        model.addAttribute("edit", true);
        return "registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating donor in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{id}-employee" }, method = RequestMethod.POST)
    public String updateDonor(@Valid Donor donor, BindingResult result,
                                 ModelMap model, @PathVariable String ssn) {

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
     * This method will delete an employee by it's SSN value.
     */
    @RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable String ssn) {
        service.deleteEmployeeBySsn(ssn);
        return "redirect:/list";
    }

}
