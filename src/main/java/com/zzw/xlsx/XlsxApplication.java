package com.zzw.xlsx;

import com.zzw.xlsx.utils.ImportData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class XlsxApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(XlsxApplication.class, args);
		ImportData bean = run.getBean(ImportData.class);
		bean.work();
	}

}
