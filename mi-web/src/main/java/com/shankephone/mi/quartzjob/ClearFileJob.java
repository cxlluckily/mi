package com.shankephone.mi.quartzjob;

import com.shankephone.mi.util.PropertyAccessor;

import java.io.File;

/**
 * @author 郝伟州
 * @date 2018年10月10日16:55:57
 */
public class ClearFileJob
{
    public void execute()
    {
        try
        {
            String path = PropertyAccessor.getProperty("clear.pathfolder");
            String[] filepathArr=path.split(";");
            for(int i=0;i<filepathArr.length;i++)
            {
                if("".equals(filepathArr[i]))
                {
                    continue;
                }
                File file=new File(filepathArr[i]);
                if (!file.exists())
                {
                   continue;
                }
                File [] filelist = file.listFiles();
                for (int n = 0; n < filelist.length; n++) {
                    if (!filelist[n].isDirectory())
                    {
                        filelist[n].delete();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
