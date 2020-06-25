package com.data.comparison.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.comparison.constants.DataComparisonConstants;

public class SendReportToMail {
	
	private static Logger logger = LogManager.getLogger(SendReportToMail.class);

	public void SendMailByAttachment(String mailAddressTo) {

		String from = DataComparisonConstants.MAIL_FROM_ADDRESS;

		final String username = DataComparisonConstants.SMTP_USERNAME;
		final String password = DataComparisonConstants.SMTP_PASSWORD;

		String host = DataComparisonConstants.HOST;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAddressTo));

			message.setSubject("Data Comparison Summary");

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("Hi, \n\n PFA - The Data Comparison Report \n\n Thanks");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = DataComparisonConstants.OUTPUT_REPORT_FILE;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);

			logger.info("Mail Sent Successfully");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
