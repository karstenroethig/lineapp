package karstenroethig.lineapp

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

class MailService {

    static transactional = false

    String sendMail( Headline headlineInstance, MailingList[] mailingLists, Boolean test ) {
		
		try {
			MailProperty mailProperty = MailProperty.find( 'from MailProperty as mp where 1 = 1' )
			
			if (!mailProperty) {
				return "${message(code: 'mailProperty.not.found.message', args: [message(code: 'mailProperty.label', default: 'MailProperty')])}";
			}

			String subject = mailProperty.subject;
			String htmlMsg = mailProperty.htmlMsg;
			String textMsg = mailProperty.textMsg;
			String htmlSignature = mailProperty.htmlSignature;
			String textSignature = mailProperty.textSignature;
			
			Set<String> tos = new HashSet<String>();
			
			if( test ) {
			
				for( String to : StringUtils.split( mailProperty.testAddresses, "," ) ) {
					tos.add( StringUtils.trim( to ) );
				}
			
			} else {
			
				for( String to : StringUtils.split( mailProperty.backupAddresses, "," ) ) {
					tos.add( StringUtils.trim( to ) );
				}
				
				for( MailingList mailingList : mailingLists ) {
				
					for( Contact contact : mailingList.contacts ) {
						tos.add( StringUtils.trim( contact.email ) );
					}
				}
			}
			
			if( tos.isEmpty() ) {
				return null;
			}
			
			String scenesStr = StringUtils.EMPTY;
			
			for( Scene scene : headlineInstance.scenes ) {
				scenesStr += "\n"
				scenesStr += StringUtils.trim( scene.body );
				scenesStr += "\n"
			}
			
			subject = StringUtils.replace( subject, "#{headline.subject}", headlineInstance.subject );
			
			if( test ) {
				subject = "[TEST] " + subject;
			}
			
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.location}", headlineInstance.location );
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.federalLand}", headlineInstance.federalLand.toString() );
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.subject}", headlineInstance.subject );
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.subHeadline}", headlineInstance.subHeadline );
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.body}", StringUtils.replace( headlineInstance.body, "\n", "<br/>" ) );
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.offerNumber}", headlineInstance.offerNumber.toString() );
			htmlMsg = StringUtils.replace( htmlMsg, "#{mail.htmlSignature}", htmlSignature );
			htmlMsg = StringUtils.replace( htmlMsg, "#{headline.scenes}", StringUtils.replace( scenesStr, "\n", "<br/>" ) );
			
			textMsg = StringUtils.replace( textMsg, "#{headline.location}", headlineInstance.location );
			textMsg = StringUtils.replace( textMsg, "#{headline.federalLand}", headlineInstance.federalLand.toString() );
			textMsg = StringUtils.replace( textMsg, "#{headline.subject}", headlineInstance.subject );
			textMsg = StringUtils.replace( textMsg, "#{headline.subject}", headlineInstance.subHeadline );
			textMsg = StringUtils.replace( textMsg, "#{headline.body}", headlineInstance.body );
			textMsg = StringUtils.replace( textMsg, "#{headline.offerNumber}", headlineInstance.offerNumber.toString() );
			textMsg = StringUtils.replace( textMsg, "#{mail.textSignature}", textSignature );
			textMsg = StringUtils.replace( textMsg, "#{headline.scenes}", scenesStr );
			
			HtmlEmail email = new HtmlEmail();
			email.setHostName( mailProperty.hostName );
			email.setSmtpPort( mailProperty.port );
			email.setAuthenticator( new DefaultAuthenticator( mailProperty.username, mailProperty.password ) );
			email.setTLS( Boolean.valueOf( mailProperty.tls ) );
			
			for( String to : tos ) {
				email.addBcc( to );
			}
			
			email.setFrom( mailProperty.fromAddress, mailProperty.fromName );
			email.setSubject( subject );
			email.setHtmlMsg( htmlMsg );
			email.setTextMsg( textMsg );
			
			email.send();
		}catch( Exception ex ) {
			log.error( ex.getMessage(), ex );
		
			return ex.getMessage()
		}
		
		return null;
	}
}
