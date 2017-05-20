package io.userhabit.kongsuny;

import io.userhabit.kongsuny.model.AppInfoModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 19..
 */
public class ExcelGenerater {

    private static final int COLUMN_INDEX_ID = 0;
    private static final int COLUMN_INDEX_TITLE = 1;
    private static final int COLUMN_INDEX_GENRE = 2;
    private static final int COLUMN_INDEX_COMPANY = 3;
    private static final int COLUMN_INDEX_DOWNLOAD = 4;
    private static final int COLUMN_INDEX_CURRENT_VERSION = 5;
    private static final int COLUMN_INDEX_UPDATE_DATE = 6;
    private static final int COLUMN_INDEX_OPERATION_VERSION = 7;
    private static final int COLUMN_INDEX_CREATE_DATE = 8;

    public ExcelGenerater() {

    }

    public File saveExcelFile(List<AppInfoModel> list) {
        File mExcelFile = null;
        try {
            Calendar cal = Calendar.getInstance();

            mExcelFile = new File(cal.getTime() + "_appinfo.xls");

            HSSFWorkbook writer = new HSSFWorkbook();
            Cell c = null;
            // 시트 헤더 스타일 정의
            Workbook myWorkBook = new HSSFWorkbook();
            CellStyle cs = myWorkBook.createCellStyle();
            cs.setFillForegroundColor(HSSFColor.LIME.index);
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            Sheet sheet = writer.createSheet("앱정보");
            Row row = sheet.createRow(0);

            c = row.createCell(COLUMN_INDEX_ID);
            c.setCellValue("ID");
            c = row.createCell(COLUMN_INDEX_TITLE);
            c.setCellValue("앱 이름");
            c = row.createCell(COLUMN_INDEX_GENRE);
            c.setCellValue("장르");
            c = row.createCell(COLUMN_INDEX_COMPANY);
            c.setCellValue("회사");
            c = row.createCell(COLUMN_INDEX_DOWNLOAD);
            c.setCellValue("다운로드 수");
            c = row.createCell(COLUMN_INDEX_CURRENT_VERSION);
            c.setCellValue("현재 버전");
            c = row.createCell(COLUMN_INDEX_UPDATE_DATE);
            c.setCellValue("업데이트 날짜");
            c = row.createCell(COLUMN_INDEX_OPERATION_VERSION);
            c.setCellValue("최소 버전");
            c = row.createCell(COLUMN_INDEX_CREATE_DATE);
            c.setCellValue("정보 등록 날짜");
            System.out.println("list size : " + list.size());
            for (int i = 1; i < list.size(); i++) {
                AppInfoModel item = list.get(i);
                row = sheet.createRow(i);

                c = row.createCell(COLUMN_INDEX_ID);
                c.setCellValue(item.getId());
                c = row.createCell(COLUMN_INDEX_TITLE);
                c.setCellValue(item.getTitle());
                c = row.createCell(COLUMN_INDEX_GENRE);
                c.setCellValue(item.getGenre());
                c = row.createCell(COLUMN_INDEX_COMPANY);
                c.setCellValue(item.getcompany());
                c = row.createCell(COLUMN_INDEX_DOWNLOAD);
                c.setCellValue(item.getDownLoads());
                c = row.createCell(COLUMN_INDEX_CURRENT_VERSION);
                c.setCellValue(item.getCurrentVersion());
                c = row.createCell(COLUMN_INDEX_UPDATE_DATE);
                c.setCellValue(item.getUpdateDate());
                c = row.createCell(COLUMN_INDEX_OPERATION_VERSION);
                c.setCellValue(item.getOperationVersion());
                c = row.createCell(COLUMN_INDEX_CREATE_DATE);
                c.setCellValue(item.getAppInfoUpdateDate());

                if(i % 100 == 0) {
                    System.out.println(i);
                }
            }

            sheet.autoSizeColumn(COLUMN_INDEX_ID);
            sheet.autoSizeColumn(COLUMN_INDEX_TITLE);
            sheet.autoSizeColumn(COLUMN_INDEX_GENRE);
            sheet.autoSizeColumn(COLUMN_INDEX_COMPANY);
            sheet.autoSizeColumn(COLUMN_INDEX_DOWNLOAD);
            sheet.autoSizeColumn(COLUMN_INDEX_CURRENT_VERSION);
            sheet.autoSizeColumn(COLUMN_INDEX_UPDATE_DATE);
            sheet.autoSizeColumn(COLUMN_INDEX_OPERATION_VERSION);
            sheet.autoSizeColumn(COLUMN_INDEX_CREATE_DATE);

            FileOutputStream os = new FileOutputStream(mExcelFile);
            writer.write(os);
            os.close();

        } catch (Exception e) {
            System.out.println("excel error : " + e.getMessage());
        }
        return mExcelFile;
    }
}
