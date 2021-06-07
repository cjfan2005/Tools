package com.cht;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LagTime {
	
	private final static String CONFIG_PATH = "D:\\config\\config.txt";
	private final static String RESULT_FILE_PATH = "D:\\config\\result\\output.txt";
	private final static long MAX_SIZE = 50 * 1024 * 1024; // 50MB.;
	
	public static void lagTimeCal() {
		try {
			File configFileName = new File(CONFIG_PATH);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(configFileName));
			BufferedReader br = new BufferedReader(reader);
			String tempString = null;
			StringBuffer sb = new StringBuffer();
			
			while ((tempString = br.readLine()) != null) {
//				System.out.println(tempString);

				String filePah = tempString;
				boolean sizeAvaiable = fileSizeCheck(filePah);
				
				if(sizeAvaiable) {
					File fileName = new File(filePah);
					
					InputStreamReader reader2 = new InputStreamReader(new FileInputStream(fileName));
					BufferedReader br2 = new BufferedReader(reader2);
					String contentString = null;
					
					long timeTaken = 0;
					long startTime = System.currentTimeMillis();

					while((contentString = br2.readLine()) != null) {}
					long endTime = System.currentTimeMillis();
					timeTaken = endTime-startTime;
					millisToDate(startTime);  millisToDate(endTime); 
					sb.append(tempString).append("\t, ")
					  .append(millisToDate(startTime)).append(", ")
					  .append(millisToDate(endTime)).append(", ")
					  .append(endTime-startTime).append("\tms\r\n");
					
				} else {
					sb.append(tempString).append("\t, ")
					  .append("ERROR: file size exceed.\r\n");
				}
			}

			BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(RESULT_FILE_PATH)));
			bwr.write(sb.toString());
			bwr.flush();
			bwr.close();
			System.out.println("Content of StringBuffer written to File.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String millisToDate(long currentTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		Date resultDate = new Date(currentTimeMillis);
//		System.out.println(sdf.format(resultDate));
		return sdf.format(resultDate);
	}
	
	public static boolean fileSizeCheck(String fileName) {
		Path path = Paths.get(fileName);

        try {
            long bytes = Files.size(path);
//            System.out.println(String.format("%,d bytes", bytes));
//            System.out.println(String.format("%,d kb", bytes / 1024));
//            System.out.println(String.format("%,d mb", bytes / (1024 * 1024)));

            if(bytes <= MAX_SIZE)
            	return true;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
	}

	public static void main(String[] args) {
		lagTimeCal();
	}
}
