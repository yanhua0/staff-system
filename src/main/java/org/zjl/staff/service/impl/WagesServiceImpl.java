package org.zjl.staff.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.zjl.staff.dao.WagesDao;
import org.zjl.staff.entity.Wages;
import org.zjl.staff.service.WagesService;
import org.zjl.staff.utils.ExampleUtils;
import org.zjl.staff.utils.POIExcelUtils;
import org.zjl.staff.utils.RandomCharUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * (Wages)表服务实现类
 *
 * @since 2022-11-12 16:38:24
 */
@Service("wagesService")
public class WagesServiceImpl implements WagesService {
    @Resource
    private WagesDao wagesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Wages queryById(Integer id) {
        return this.wagesDao.selectByPrimaryKey(id);
    }

    /**
     * 查询
     *
     * @param wages 筛选条件
     * @return 查询结果
     */
    @Override
    public List<Wages> queryAll(Wages wages) {
        wages.setEnabled(1);
        return this.wagesDao.selectByExample(ExampleUtils.getExampleV2(wages));
    }

    /**
     * 新增数据
     *
     * @param wages 实例对象
     * @return 实例对象
     */
    @Override
    public Wages insert(Wages wages) {
        buildWages(wages);
        this.wagesDao.insertSelective(wages);
        return wages;
    }

    private void buildWages(Wages wages) {
        wages.setDepName("泥工班一工区");
        wages.setDescription("高科江澜三四期二项目泥工");
        wages.setEnabled(1);
        LocalDate localDate = LocalDate.parse(wages.getSettlementTime() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        String calTimeFormat = "%s.%s.01-%s.%s.%s";
        String calTime = String.format(calTimeFormat, lastDay.getYear(), localDate.getMonthValue(), localDate.getYear(), lastDay.getMonthValue(), lastDay.getDayOfMonth());
        wages.setCalTime(calTime);
    }

    /**
     * 修改数据
     *
     * @param wages 实例对象
     * @return 实例对象
     */
    @Override
    public void update(Wages wages) {
        wagesDao.updateByPrimaryKeySelective(wages);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public void deleteById(Integer id) {
        Wages wages = queryById(id);
        wages.setEnabled(0);
        wagesDao.updateByPrimaryKeySelective(wages);
    }

    @Override
    public void insertQuick(String settlementTime, String targetTime) {
        Wages wages = new Wages();
        wages.setSettlementTime(targetTime);
        List<Wages> wagesList = queryAll(wages);
        for (Wages wages1 : wagesList) {
            wages1.setSettlementTime(settlementTime);
            buildWages(wages1);
        }
       wagesDao.insertList(wagesList);
    }

    public void export(Wages wages) {
        List<Wages> wagesList = queryAll(wages);
        String fileName = "template.xlsx";
        XSSFWorkbook wb = POIExcelUtils.creatWorkbook(fileName);
        if (!CollectionUtils.isEmpty(wagesList)) {
            // 建立新的sheet对象
            Sheet sheet0 = wb.getSheetAt(0);
            Sheet sheet1 = wb.getSheetAt(1);
            String time = wagesList.get(0).getSettlementTime() + "-01";
            LocalDate localDate = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int sheetStartRow = 4;
            for (int i = 0; i < wagesList.size(); i++) {
                Wages w = wagesList.get(i);
                int rowNum = i + 1;
                Row row = sheet0.createRow(rowNum);
                Map<Integer, String> valueMap = getIntegerStringMap(w);
                for (int j = 0; j < 13; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(valueMap.get(j));
                }
                buildRow0(sheet1, wb, localDate.getYear(), localDate.getMonthValue());
                buildRow2(sheet1, wb, localDate.getMonthValue());
                sheetStartRow = buildSheet1(sheet1, sheetStartRow, i, w);
            }
            String name = "%s月高科江澜三、四期二项目";
            POIExcelUtils.getOutStreamResponse(wb, String.format(name, localDate.getMonthValue()));
        } else {
            POIExcelUtils.getOutStreamResponse(wb, "无数据导出.xlsx");
        }

    }

    @Override
    public Wages getByName(String name) {
        Wages wages=new Wages();
        wages.setStaffName(name);
        return Optional.ofNullable(queryAll(wages)).filter(e->!CollectionUtils.isEmpty(e)).map(e->e.get(0)).orElse(new Wages());
    }

    private int buildSheet1(Sheet sheet1, int sheetStartRow, int i, Wages w) {
        Row rowSheet1 = sheet1.getRow(sheetStartRow);
        //后续如果超过了61行在说
        sheetStartRow++;
        Map<Integer, String> valueMap2 = RandomCharUtils.get(31, 2, w.getDays());
        for (int j = 0; j < 35; j++) {
            Cell cell = rowSheet1.getCell(j);
//                    XSSFCellStyle cellStyle = wb.createCellStyle();
//                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
//                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//                    cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//                    cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//                    cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
//                    cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            //  cell.setCellStyle(cellStyle);
            if (j == 0) {
                //   setFont(wb, cellStyle,10);
                cell.setCellValue(i + 1);
            } else if (j == 1) {
                // setFont(wb, cellStyle,11);
                cell.setCellValue(w.getStaffName());
            } else if (j == 33) {
                cell.setCellValue("");
            } else if (j == 34) {
                //  setFont(wb, cellStyle,10);
                cell.setCellValue(w.getDays());
            } else {
                //   setFont(wb, cellStyle,12);
                cell.setCellValue(valueMap2.get(j));
            }
        }
        return sheetStartRow;
    }

    private void buildRow0(Sheet sheet, XSSFWorkbook workbook, int year, int month) {
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        cell.setCellValue(String.format("高科江澜工程 %s年 %s月民工考勤表", year, month));
    }

    private void buildRow2(Sheet sheet, XSSFWorkbook workbook, int month) {
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(2);
        cell.setCellValue(String.format("%s月", month));
    }

    private void setFont(XSSFWorkbook wb, XSSFCellStyle cellStyle, int fontSize) {
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) fontSize);  //字体大小
        cellStyle.setFont(font);//选择需要用到的字体格式
    }

    private Map<Integer, String> getIntegerStringMap(Wages w) {
        Map<Integer, String> valueMap = new HashMap<>();
        valueMap.put(0, "泥工班一工区");
        valueMap.put(1, w.getStaffName());
        valueMap.put(2, w.getIdNumber());
        valueMap.put(3, w.getBankDesc());
        valueMap.put(4, w.getBankNumber());
        valueMap.put(5, w.getCalTime());
        valueMap.put(6, w.getMoney());
        valueMap.put(7, "0");
        valueMap.put(8, "0");
        valueMap.put(9, w.getMoney());
        valueMap.put(10, "0");
        valueMap.put(11, w.getPhoneNumber());
        valueMap.put(12, w.getDescription());
        return valueMap;
    }
}
