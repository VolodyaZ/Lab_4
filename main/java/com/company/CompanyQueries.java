package com.company;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
        List<Company> result = new ArrayList<>();
        for (Company company : companies) {
            if (company.getFoundationDate().after(Date.from(from.toInstant().minus(1, ChronoUnit.DAYS))) &&
                    company.getFoundationDate().before(Date.from(till.toInstant().plus(1, ChronoUnit.DAYS)))) {
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
