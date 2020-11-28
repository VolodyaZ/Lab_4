package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String inputFile;
        String outputFile;
        String logFile;
        if (args.length < 3) {
            inputFile = "in.csv";
            outputFile = "out.csv";
            logFile = "log.txt";
        } else {
            inputFile = args[0];
            outputFile = args[1];
            logFile = args[2];
        }
        try {
            CompanyQueries companies = readTable(inputFile);
            processQueries(companies, outputFile, logFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static CompanyQueries readTable(String inputFile) throws Exception {
        CompanyQueries table = new CompanyQueries();
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                table.addCompany(new Company(scanner.nextLine()));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return table;
    }

    public static void processQueries(CompanyQueries companies, String outputFile, String logFile) throws IOException {
        //process queries here
        String query = "";
        try (Scanner scanner = new Scanner(System.in);
             BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile));
             BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true))
             ) {
            System.out.println("Available queries: \n" + Queries.SHORTNAME.getValue() + " String, " +
                    Queries.INDUSTRY.getValue() + " String, " +
                    Queries.TYPE_OF_BUSINESS.getValue() + " String, " +
                    Queries.FOUNDATION_DATE.getValue() + " Date Date, " +
                    Queries.NUMBER_OF_EMPLOYEES.getValue() + " int int, " +
                    Queries.EXIT.getValue());
            while (!(query = scanner.next()).equals(Queries.EXIT.getValue())) {
                List<Company> result;
                Queries q;
                try {
                    q = Queries.valueOf(query);
                } catch (IllegalArgumentException ex) {
                    scanner.nextLine();
                    System.out.println("query " + query + " not found\n");
                    printLog(query, logWriter, true);
                    continue;
                }
                switch (q) {
                    case SHORTNAME:
                        String sName = scanner.next();
                        query += " " + sName;
                        result = companies.findByShortName(sName);
                        break;
                    case INDUSTRY:
                        String industry = scanner.next();
                        query += " " + industry;
                        result = companies.findByIndustry(industry);
                        break;
                    case TYPE_OF_BUSINESS:
                        String type = scanner.next();
                        query += " " + type;
                        result = companies.findByTypeOfBusiness(type);
                        break;
                    case FOUNDATION_DATE:
                        Date start = new SimpleDateFormat(DateFormat.FORMAT).parse(scanner.next());
                        Date end = new SimpleDateFormat(DateFormat.FORMAT).parse(scanner.next());
                        query += " " + start.toString() + " " + end.toString();
                        result = companies.findByDateOfFoundation(start, end);
                        break;
                    case NUMBER_OF_EMPLOYEES:
                        int min = scanner.nextInt();
                        int max = scanner.nextInt();
                        query += " " + min + " " + max;
                        result = companies.findByNumberOfEmployees(min, max);
                        break;
                    default:
                        continue;
                }
                printLog(query, logWriter, false);
                printOutput(result, query, outputWriter);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            if (query.length() > 0) {
                BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true));
                printLog(query, logWriter, true);
                logWriter.close();
            }
        }
    }

    private static void printLog(String str, BufferedWriter writer, boolean failed) throws IOException {
        String result = failed ? "failed" : "succeeded";
        writer.write(str + " " + LocalDateTime.now() + "  -- " + result + '\n');
    }

    private static void printOutput(List<Company> result, String query, BufferedWriter writer) throws IOException {
        writer.write(query + "\n");
        for (Company company : result) {
            writer.write(company.toString() + '\n');
        }
    }

//    static final String SHORTNAME = "SHORTNAME";
//    static final String INDUSTRY = "INDUSTRY";
//    static final String TYPE_OF_BUSINESS = "TYPE_OF_BUSINESS";
//    static final String FOUNDATION_DATE = "FOUNDATION_DATE";
//    static final String NUMBER_OF_EMPLOYEES = "NUMBER_OF_EMPLOYEES";
//    static final String EXIT = "EXIT";

    public enum Queries {
        SHORTNAME("SHORTNAME"),
        INDUSTRY("INDUSTRY"),
        TYPE_OF_BUSINESS("TYPE_OF_BUSINESS"),
        FOUNDATION_DATE("FOUNDATION_DATE"),
        NUMBER_OF_EMPLOYEES("NUMBER_OF_EMPLOYEES"),
        EXIT("EXIT");

        private final String value;

        Queries(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
