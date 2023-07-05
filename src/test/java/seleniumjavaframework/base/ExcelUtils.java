package seleniumjavaframework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	File file;
	FileOutputStream fo;
	XSSFWorkbook workbook;
	Workbook book;

	public void writeToExcel(HashMap<String, List<String>> records) {
		file = new File("outcome.xlsx");
		boolean flag = false;
		if (file.exists()) {
			flag = true;
		}
		List<List<String>> listValues = new ArrayList<List<String>>(records.values());
		List<String> testname = new ArrayList<String>();
		List<String> status = new ArrayList<String>();
		List<String> datetime = new ArrayList<String>();
		
		int Size = listValues.size();
		int val = Size - listValues.size();
		for (int i = 0; i < listValues.get(val).size(); i++) {
			testname.add(listValues.get(val).get(i));
		}
		val++;

		for (int i = 0; i < listValues.get(val).size(); i++) {
			status.add(listValues.get(val).get(i));
		}
		val++;
		for (int i = 0; i < listValues.get(val).size(); i++) {
			datetime.add(listValues.get(val).get(i));
		}

		
		if (!flag) {
			try {
				fo = new FileOutputStream(file);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Results");

			List<String> keyLists = new ArrayList<String>(records.keySet());

			XSSFRow row = sheet.createRow(0);
			for (int i = 0; i < keyLists.size(); i++) {
				row.createCell(i).setCellValue(keyLists.get(i));
			}

			

			for (int i = 0; i < testname.size(); i++) {
				sheet.createRow(i + 1).createCell(0).setCellValue(testname.get(i));
			}
			for (int i = 0; i < status.size(); i++) {
				sheet.getRow(i + 1).createCell(1).setCellValue(status.get(i));
			}
			for (int i = 0; i < datetime.size(); i++) {
				sheet.getRow(i + 1).createCell(2).setCellValue(datetime.get(i));
			}
			try {
				workbook.write(fo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			
			System.out.println("File Already Exists");
			FileInputStream fi = null;
			try {
				fi = new FileInputStream(file);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			try {
				book = WorkbookFactory.create(fi);
			} catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Sheet inputsheet = book.getSheet("Results");
			int row = inputsheet.getLastRowNum();
			int cols = inputsheet.getRow(0).getLastCellNum();
			System.out.println(row);
			try {
				fi.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				int row1Copy = row;
			for (int i = 0; i < testname.size(); i++) {
				
				inputsheet.createRow(++row1Copy).createCell(0).setCellValue(testname.get(i));
			}
			int row2Copy = row;
			for (int i = 0; i < status.size(); i++) {
				
				inputsheet.getRow(++row2Copy).createCell(1).setCellValue(status.get(i));
			}
			int row3Copy = row;
			for (int i = 0; i < datetime.size(); i++) {
				
				inputsheet.getRow(++row3Copy).createCell(2).setCellValue(datetime.get(i));
			}
			try {
				fo = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				book.write(fo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				book.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
