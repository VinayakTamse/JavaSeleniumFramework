package seleniumjavaframework.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;


public class Reporting implements ITestListener {
	
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentKlovReporter klov;
	public HashMap<String,String> record;
	public HashMap<String,List<String>> nerec;
	public List<String> datetime;
	public List<String> testcases;
	public List<String> status;
	public File file1;
	public FileOutputStream file;
	public FileInputStream fileIn;
	public XSSFWorkbook workbook;
	public Workbook book;
	
	
	
	public String getDateTime()
	{
		LocalDate localdate = LocalDate.now();
		LocalTime localtime = LocalTime.now();
		String date = localdate.getDayOfMonth() +"-" +localdate.getMonth() +"-"+localdate.getYear();
		String time = localtime.getHour() + "-" +localtime.getMinute() + "-"+ localtime.getSecond();
		return date+"-"+time;
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		
		System.out.println(result.getName()+" Test Started");
		testcases.add(result.getName());
		datetime.add(getDateTime());
		
		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extent.createTest(result.getName())
		.log(Status.PASS, MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
		record.put(result.getName(), "PASSED");
		status.add("PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extent.createTest(result.getName())
		.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().getLocalizedMessage(), ExtentColor.RED));
		record.put(result.getName(), "FAILED");
		status.add("FAILED");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extent.createTest(result.getName())
		.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped", ExtentColor.YELLOW));
		record.put(result.getName(), "SKIPPED");
		status.add("SKIPPED");
	}

	@Override
	public void onStart(ITestContext context) {
		spark = new ExtentSparkReporter(System.getProperty("user.dir")+"\\src\\test\\resources\\reports\\ExtentReport-"+getDateTime()+".html")
				.viewConfigurer()
				.viewOrder()
				.as(new ViewName [] {ViewName.DASHBOARD, ViewName.TEST, 
						   ViewName.AUTHOR, 
						   ViewName.DEVICE, 
						   ViewName.EXCEPTION, 
						   ViewName.LOG })
				.apply();
		
		klov = new ExtentKlovReporter("OrangeHRM");
		klov.initMongoDbConnection("localhost",27017);
		klov.initKlovServerConnection("http://localhost:80");
		
		extent = new ExtentReports();
		extent.setSystemInfo("Name", "Vinayak");
		extent.attachReporter(spark,klov);
		record = new HashMap<String,String>();
		record.put("Test Case Name", "Status");
		testcases = new ArrayList<String>();
		status = new ArrayList<String>();
		datetime = new ArrayList<String>();
		
		
		
		
		
	}

	@Override
	public void onFinish(ITestContext context) {
		nerec = new HashMap();
		nerec.put("Test Case Name", testcases);
		nerec.put("Status", status);
		nerec.put("Date", datetime);
		System.out.println(nerec);
		ExcelUtils ex = new ExcelUtils();
		ex.writeToExcel(nerec);
		System.out.println(record);
		file1 = new File("text.xlsx");
		if (! file1.exists()) {
			
		System.out.println("File Not Exists== Creating file");
		
		try {
			file = new FileOutputStream(file1);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
			workbook = new XSSFWorkbook();
		
		int rowno=0;
		XSSFSheet sheet = workbook.createSheet("Results");
		Set<Entry<String,String>>maps = record.entrySet();
		for (Entry<String, String> nmaps: maps)
		{
			XSSFRow row = sheet.createRow(rowno++);
			row.createCell(0).setCellValue(nmaps.getKey());
			row.createCell(1).setCellValue(nmaps.getValue());
		}
		try {
			workbook.write(file);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			workbook.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		}
		else {
			System.out.println("File exists== Updating file");
			record.remove("Test Case Name");
			try {
				fileIn = new FileInputStream(file1);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			try {
				book = WorkbookFactory.create(fileIn);
			} catch (EncryptedDocumentException e1) {
				System.out.println(e1.getLocalizedMessage());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			Sheet resheet = book.getSheet("Results");
			int rowNum = resheet.getLastRowNum();
			int cellNum = resheet.getRow(0).getLastCellNum();
			Set<Entry<String, String>> entrymap = record.entrySet();
			for (Entry<String,String> em : entrymap)
			{
				Row row1 = resheet.createRow(++rowNum);
				row1.createCell(0).setCellValue(em.getKey());
				row1.createCell(1).setCellValue(em.getValue());
			}
			
			try {
				fileIn.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			FileOutputStream os = null;
			
			try {
				os = new FileOutputStream(file1);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			try {
				book.write(os);
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
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		extent.flush();
		
	}

}
