package com.shankephone.mi.test.model;

import com.shankephone.mi.util.DataSwitch;
import org.junit.Test;

/**
 * @author 赵亮
 * @date 2018-06-12 14:49
 */

public class SysUserTest
{


    @Test
    public void queryBuild()
    {
        String numberStr = "001002";
        System.out.println(getNextNodeCode(numberStr));
        numberStr = "001009";
        System.out.println(getNextNodeCode(numberStr));
        numberStr = "001089";
        System.out.println(getNextNodeCode(numberStr));
        numberStr = "001099";
        System.out.println(getNextNodeCode(numberStr));
        //        System.out.println(getNextNodeCode("001002"));
    }

    public String getNextNodeCode(String internalNumber)
    {
        String endThreeNumber = internalNumber.substring(internalNumber.length() - 3, internalNumber.length());
        Integer nextNumber = DataSwitch.convertObjectToInteger(endThreeNumber) + 1;
        String beginNumber = internalNumber.substring(0, internalNumber.length() - 3);
        //        System.out.println("beginNumber"+beginNumber);
        if (nextNumber < 10)
        {
            return beginNumber + "00" + nextNumber;
        }
        else if (nextNumber < 100)
        {
            return beginNumber + "0" + nextNumber;
        }
        else
        {
            return beginNumber + nextNumber.toString();
        }
    }


}