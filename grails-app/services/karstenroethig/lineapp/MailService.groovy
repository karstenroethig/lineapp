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
			
			subject = StringUtils.replace( subject, "#{headline.subject}", StringUtils.replace( headlineInstance.subject, "\n", " " ) );
			
			if( test ) {
				subject = "[TEST] " + subject;
			}
			
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.location}", headlineInstance.location );
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.federalLand}", headlineInstance.federalLand.toString() );
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.subject}", headlineInstance.subject );
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.subHeadline}", headlineInstance.subHeadline );
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.body}", headlineInstance.body );
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.offerNumber}", headlineInstance.offerNumber.toString() );
			htmlMsg = replaceForHtml( htmlMsg, "#{headline.scenes}", scenesStr );
			htmlMsg = StringUtils.replace( htmlMsg, "#{mail.htmlSignature}", htmlSignature );
			
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
	
	private String replaceForHtml( String text, String searchString, String replacement ) {
		
		if( replacement == null ) {
			replacement = StringUtils.EMPTY;
		}
		
		replacement = replacement.encodeAsHTML();
		replacement = StringUtils.replace( replacement, "\n", "<br/>" )
		
		return StringUtils.replace( text, searchString, replacement );
	}
}
