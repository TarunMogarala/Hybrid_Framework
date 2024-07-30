package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	
	XSSFWorkbook wb;
	
	public ExcelFileUtil(String path) throws Throwable {
		FileInputStream file=new FileInputStream(path);
		wb=new XSSFWorkbook(file);
	}
	
	public int Count(String name) {
	return wb.getSheet(name).getLastRowNum();
	}
	
	public String GetCellData(String name,int row,int col) {
		String data="";
		if(wb.getSheet(name).getRow(row).getCell(col).getCellType()==CellType.NUMERIC) {
			int cell=(int) wb.getSheet(name).getRow(row).getCell(col).getNumericCellValue();
			data =String.valueOf(cell);
		}else {
			data=wb.getSheet(name).getRow(row).getCell(col).getStringCellValue();
		}
		return data;
	}
	
	public void SetCell(String name,int row,int col,String status,String writepath) throws Throwable {
		XSSFSheet ws=wb.getSheet(name);
		XSSFRow rows=ws.getRow(row);
		XSSFCell cell=rows.createCell(col);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass")) {
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(col).setCellStyle(style);
		}else if(status.equalsIgnoreCase("Fail")) {
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(col).setCellStyle(style);
		}else if(status.equalsIgnoreCase("Blocked")) {
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(col).setCellStyle(style);
		}
		FileOutputStream fo=new FileOutputStream(writepath);
		wb.write(fo);
	}
	
}