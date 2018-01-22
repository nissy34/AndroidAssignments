package main;


import communities.*;

import java.sql.Date;

public class manage_members
{
    public static void main(String[] str) throws Exception
    {
        cammunity m = new cammunity();
        m.addMember(new Married(1,"g","2","hjh",new Date(1079,8,8),100,100,7,valnteer.MUSICAL,9,0));
        m.addMember(new Married(1,"g","2","hjh",new Date(1079,8,8),100,100,7,valnteer.MUSICAL,9,0));
        System.out.println(m.totalTaxes());
        System.out.println(m.valList());
    }
}
