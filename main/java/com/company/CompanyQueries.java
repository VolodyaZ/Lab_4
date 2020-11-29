package com.company;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CompanyQueries {
    private final List<Company> companies;

    CompanyQueries() {
        this.companies = new ArrayList<>();
    }

    CompanyQueries(List<Company> companies) {
        this.companies = new ArrayList<>(companies);
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public List<Company> getCompanies() {
        return new ArrayList<>(companies);
    }

    public List<Company> findByShortName(String shortName) {
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getShortName().toLowerCase().equals(shortName.toLowerCase())) {
                result.add(company);
            }
        }
        return result;
    }

    public List<Company> findByIndustry(String industry) {
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getIndustry().toLowerCase().compareTo(industry.toLowerCase()) == 0) {
                result.add(company);
            }
        }
        return result;
    }

    public List<Company> findByTypeOfBusiness(String business) {
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getTypeOfBusiness().toLowerCase().compareTo(business.toLowerCase()) == 0) {
                result.add(company);
            }
        }
        return result;
    }

    public List<Company> findByDateOfFoundation(Date from, Date till) {
        LocalDate localDateFrom = LocalDate.ofInstant(from.toInstant(), ZoneId.systemDefault()).minusDays(1);
        LocalDate localDateTill = LocalDate.ofInstant(till.toInstant(), ZoneId.systemDefault()).plusDays(1);
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getFoundationDate().after(Date.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant())) &&
                    company.getFoundationDate().before(Date.from(localDateTill.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                result.add(company);
            }
        }
        return result;
    }

    public List<Company> findByNumberOfEmployees(int min, int max) {
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getEmployeeCount() >= min && company.getEmployeeCount() <= max) {
                result.add(company);
            }
        }
        return result;
    }
}
