package DAO;

import Beans.Company;

import java.util.ArrayList;

/**
 * interface that holds the methods regarding companies in the database that will be implemented in the DBDao
 * (including full CRUD functionality)
 */
public interface CompaniesDAO {
    /**
     * tries to get a company by email and password to use with login.
     *
     * @param email    company client email.
     * @param password company client password.
     * @return Company if a company was found, else returns null.
     */
    Company isCompanyExists(String email, String password);

    /**
     * adds company to the database.
     *
     * @param company company to add.
     * @return true if adding was successful, false if not.
     */
    boolean addCompany(Company company);

    /**
     * deletes company from the database.
     *
     * @param companyID company to deleteby companyID.
     */
    void deleteCompany(long companyID);

    /**
     * get an arraylist of all companies from the DateBase.
     *
     * @return an arraylist holding all the companies.
     */
    ArrayList<Company> getAllCompanies();

    /**
     * update a company details in the database. find the company to update by id.
     *
     * @param company company with updated details.
     */
    void updateCompany(Company company);

    /**
     * get one company from the database by companyID. returns null if company was not found.
     *
     * @param companyID the id to search for.
     * @return the company retrieved by the query, returns null if no company was found.
     */
    Company getOneCompany(long companyID);
}
