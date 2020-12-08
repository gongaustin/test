package com.gongjun.changsha.EbookHandler;

import com.aspose.cells.License;

import java.io.InputStream;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 16:12 2020/12/3
 */
public class LicenseUtil {
    public static boolean GetLicense() {
        boolean result = false;
        try {
            InputStream license = LicenseUtil.class.getClassLoader().getResourceAsStream("\\license.xml"); // license路径
            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
