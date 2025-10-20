package com.nilayjain.project.uber.uberApplication;

import com.nilayjain.project.uber.uberApplication.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.export.elastic.ElasticMetricsExportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;
	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"xicet56120@datoinf.com",
				"This is testing email",
				"Body of my email");
	}
	@Test
	void sendEmailMultiple() {
		String emails[]={
				"xicet56120@datoinf.com",
				"nilayj30@gmail.com"
		};
		emailSenderService.sendEmail(emails,
				"THis is testing multi email",
				"Body of my multi email");
	}
}
