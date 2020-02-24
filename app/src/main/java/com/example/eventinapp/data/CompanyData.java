package com.example.eventinapp.data;

import com.example.eventinapp.R;
import com.example.eventinapp.model.Company;

import java.util.ArrayList;

public class CompanyData {
    private static String[] companyNames = {
          "XL Axiata",
          "Jenius",
          "Bank Mandiri",
          "J.Co",
          "Pertamina"
    };

    private static int[] companyImages = {
            R.drawable.xl_logo,
            R.drawable.jenius_logo,
            R.drawable.bank_mandiri_logo,
            R.drawable.jco,
            R.drawable.logo_pertamina

    };

    private static String[] companyDescribe = {
            "Proposers are expected to be willing to work with us, provided we offer a package partnership\n" +
                    "\n" +
                    "Normal Package :\n" +
                    "\n" +
                    "Rp. 1.500.000 /\n" +
                    "100 Prepaid Card XL Provider \n" +
                    "which is must be sold Rp. 35.000 each card.\n" +
                    "\n" +
                    "VIP Package :\n" +
                    "\n" +
                    "Rp. 2.000.000 /\n" +
                    "100 VIP Prepaid Card XL Provider \n" +
                    "which is must be sold Rp. 45.000 each card.\n",
            "Proposers are expected to be willing to work with us, provided we offer a package partnership\n" +
                    "\n" +
                    "Normal Package :\n" +
                    "\n" +
                    "Rp. 1.000.000 /\n" +
                    "50 new member of Jenius \n",
            "Proposers are expected to be willing to work with us, provided we offer a package partnership\n" +
                    "\n" +
                    "Normal Package :\n" +
                    "\n" +
                    "Rp. 500.000 /\n" +
                    "10 New Member of Bank Mandiri \n" ,
            "Proposers are expected to be willing to work with us, provided we offer a package partnership\n" +
                    "\n" +
                    "Normal Package :\n" +
                    "\n" +
                    "Rp. 1.000.000 \n" +
                    "We open our stand in your event\n" ,
            "Proposers are expected to be willing to work with us, provided we offer a package partnership\n" +
                    "\n" +
                    "Maximal amount :\n" +
                    "\n" +
                    "Rp. 1.000.000 \n" +
                    "Just send your proposal to our company\n"
    };


    public static ArrayList<Company> getListData() {
        ArrayList<Company> list = new ArrayList<>();
        for (int position = 0; position < companyNames.length; position++) {
            Company company = new Company();
            company.setName(companyNames[position]);
            company.setPhoto(companyImages[position]);
            company.setDescribe(companyDescribe[position]);
            list.add(company);
        }
        return list;
    }
}
