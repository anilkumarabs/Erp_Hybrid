package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.reporters.jq.Main;

public class ExcelFileUtil
{
Workbook wb;
public ExcelFileUtil(String ExcelPath) throws Throwable
{
	FileInputStream fi = new FileInputStream(ExcelPath);
	wb=WorkbookFactory.create(fi);
	}
public int Rowcount(String Sheetname)
{
	
	return wb.getSheet(Sheetname).getLastRowNum();
}
public String  getCellData(String Sheetname,int row,int column)
{
	String data ="";
	if(wb.getSheet(Sheetname).getRow(row).getCell(column).getCellType()== CellType.NUMERIC)
	{
		int celldata=(int) wb.getSheet(Sheetname).getRow(row).getCell(column).getNumericCellValue();
		data=String.valueOf(celldata);
	}else
		{data =wb.getSheet(Sheetname).getRow(row).getCell(column).getStringCellValue();
}return data;
}
public void setCellData(String Sheetname,int row,int column,String status,String writeexcelpath) throws Throwable
{
	Sheet ws= wb.getSheet(Sheetname);
	Row rownum = ws.getRow(row);
	Cell  data = rownum.createCell(column);
	data.setCellValue(status);
	if(status.equalsIgnoreCase("pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		font.setColor (IndexedColors.GREEN.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);
	}else if(status.equalsIgnoreCase("fail"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		font.setColor (IndexedColors.RED.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);	
	}else if (status.equalsIgnoreCase("blocked"))
	{
		CellStyle style = wb.createCellStyle();
		Font font =wb.createFont();
		font.setColor (IndexedColors.BLUE.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);	

	}
	FileOutputStream fo = new FileOutputStream(writeexcelpath);
	wb.write(fo);
}
public static void main(String[] args) throws Throwable
{
	ExcelFileUtil xl=new ExcelFileUtil("C:\\Users\\HP\\eclipse project\\sample_lyst9730.xlsx");
	int rc =xl.Rowcount("Emp");
	System.out.println(rc);
	for( int i=1;i<=rc;i++)
	{
	String	fname=xl.getCellData("Emp", i, 0);
	String	mname=xl.getCellData("Emp", i, 1);	
	String	lname=xl.getCellData("Emp", i, 2);
	String	eid=xl.getCellData("Emp", i, 3);
	System.out.println(fname+"     "+mname+"     "+lname+"    "+eid);
	//xl.setCellData("Emp", i, 4, "pass", "C:/Users/HP/eclipse project/sample_lyst9730.xlsx");
	//xl.setCellData("Emp", i, 4, "fail", "C:/Users/HP/eclipse project/sample_lyst9730.xlsx");
	xl.setCellData("Emp", i, 4, "blocked", "C:/Users/HP/eclipse project/sample_lyst9730.xlsx");
	}
}
}
