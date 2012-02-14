import grails.util.GrailsUtil
import karstenroethig.lineapp.*

class BootStrap {

	def init = { servletContext ->
		
		switch( GrailsUtil.environment ) {
		
			case "development":
				
				if( !PropertyNumber.findByKey( 'headline.key.next' ) ) {
					PropertyNumber prop = new PropertyNumber( key: 'headline.key.next', value: 20241L )
					
					if( !prop.save() ) {
						log.error "SAVING OF PROPERTYNUMBER FAILED:\n ${prop.errors}"
						return
					}
				}
				
				// is data in DB already available?
				if( User.findByUsername( 'admin' ) ) {
					log.info 'Database is initialized already!'
					return
				}
				
				// create first user (in admin-role)
				User admin = new User( username: 'admin',
					passwordHash: 'geheim'.encodeAsSHA(),
					fullname: 'Ad Ministrator',
					email: 'name@company.com',
					role: 'admin',
					status: UserStatus.ACTIVE )
				
				if( !admin.save() ) {
					log.error "SAVING OF ADMIN FAILED:\n ${admin.errors}"
					return
				}
								
				// create first user (in user-role)
				User user = new User( username: 'jdoe',
					passwordHash: 'passwort'.encodeAsSHA(),
					fullname: 'John Doe',
					email: 'name@company.com',
					role: 'user',
					status: UserStatus.ACTIVE )
				
				if( !user.save() ) {
					log.error "SAVING OF USER FAILED:\n ${user.errors}"
					return
				}
				
				// create a headline (in user-role)
				Headline headline = new Headline()
				headline.subject = 'Autobahn wird zur Schotterpiste: LKW schleudert nach Reifenplatzer gegen Mittelleitplanke und kippt um - 20 Tonnen Schotter auf Fahrspuren in beiden Richtungen verteilt'
				headline.subHeadline = 'Brummifahrer verletzt / Autobahnpolizei: "Zum Unglück wurden keine nachfolgenden Fahrzeuge in den Unfall verwickelt." / Aufräumarbeiten bis zum Abend'
				headline.body = 'Auf der Autobahn 143 zwischen Hollleben und Teutschenthal ist am Mittwochmittag ein Laster verunglückt. Gegen 12 Uhr war bei dem Brummi, der Richtung Halle unterwegs war, vorne links ein Reifen geplatzt. Der Fahrer verlor dadurch die Kontrolle über seinen Laster. Der LKW knallte gegen die Mittelleitplanke und kippte um. Er machte sich über Spuren beider Richtungsfahrbahnen breit. Die Ladung - 20 Tonnen Schotter - verteilten sich auf dem Asphalt. Laut Polizei wurde der Brummifahrer verletzt, wie schwer konnte ein Sprecher noch nicht sagen. Der Mann kam ins Bergmannstrost nach Halle. Für die Aufräumarbeiten musste die Autobahn voll gesperrt werden. Helfer mussten den Schotter von der Straße schaufeln. Außerdem wurde ein Kran geordert, um den Unglückslaster zu bergen. Bis zum frühen Abend - so hoffen die Einsatzkräfte - soll die Unfallstelle wieder frei sein.'
				headline.federalLand = FederalLand.ST
				headline.location = 'A 143 bei Teutschenthal'
				headline.recordingDate = new Date()
				headline.comment = 'Hier könnte ein Kommentar stehen.'
				headline.author = user
				
				synchronized( this ) {
					PropertyNumber prop = PropertyNumber.findByKey( 'headline.key.next' )
					headline.key = prop.value
					prop.value = prop.value + 1
					prop.save()
				}
				
				headline.save()
				
				if( headline.hasErrors() ) {
					log.error "SAVING OF HEADLINE FAILED:\n ${headline.errors}"
					return
				}
				
				// create scenes for the headline above
				Scene scene = new Scene()
				scene.body = 'Totale der Autobahn mit LKW, Polizisten und Bergungsfahrzeugen, 20 Tonnen Schotter breit über die Autobahn verteilt - diverse Einstellungen, Schwenk über LKW, leere Ladefläche vom LKW, Führerhaus des LKW mit diversen Schäden, zerstörte Leitplanke, Mitarbeiter vom Bergedienst schaufeln Granulat weg - schaufeln Bindemitteln von der Autobahn runter, Schwenk von Fahrbahn auf geplatzen Reifen, zerfetzter Reifen close, Polizisten an der Unfallstelle - im Gespräch, Kehrmaschine fährt auf Autobahn'
				scene.headline = headline
				
				if( !scene.save() ) {
					log.error "SAVING OF SCENE FAILED:\n ${scene.errors}"
					return
				}
				
				scene = new Scene()
				scene.body = 'O-Töne von Axel Dietrich, Autobahnpolizei Weißenfels, sinngemäß:...der LKW hinter mir war Richtung Halle unterwegs, aufgrund eines Reifenplatzers links verlor der Fahrer die Kontrolle über sein Fahrzeug, schleuderte gegen die Mittelleitplanke und kippte quer über die Fahrbahnen um...dabei verlor er seine Ladung und 20 Tonnen Schotter verteilten sich über der gesamten Fahrbahn...dazu trat noch eine große Menge Diesel aus...die jetzt nach und nach beseitigt wird...es muss der Schotter abgetragen werden, ein Kran zur Bergung des LKW ist angefordert...kurz nach 12 Uhr war der Unfall, hoffen dass wir bis zum Einbruch der Dunkelheit fertig sind und die Autobahn wieder für den Verkehr freigeben können...der Fahrer ist mit Verletzungen ins Krankenhaus nach Halle gebracht worden...wissen aktuell noch nicht wie schwer die Verletzungen sind...man kann von Glück reden, dass nachkommende Fahrzeugen aus beiden Richtungen nicht in den Unfall verwickelt wurden und es somit halbswegs glimpflich abgelaufen ist...'
				scene.headline = headline
				
				if( !scene.save() ) {
					log.error "SAVING OF SCENE FAILED:\n ${scene.errors}"
					return
				}
				
				// create some contacts
				def contact1 = new Contact( name: 'Kontakt 1', email: 'name@company.com' )
				contact1.save()
				def contact2 = new Contact( name: 'Kontakt 2', email: 'name@company.com' )
				contact2.save()
				def contact3 = new Contact( name: 'Kontakt 3', email: 'name@company.com' )
				contact3.save()
				
				// create a mailing list
				MailingList mailingList = new MailingList( name: 'Verteiler 1' )
				mailingList.addToContacts( contact1 )
				mailingList.addToContacts( contact2 )
				
				if( !mailingList.save() ) {
					log.error "SAVING OF MAILING LIST FAILED:\n ${mailingList.errors}"
					return
				}
				
				mailingList = new MailingList( name: 'Verteiler 2' )
				mailingList.addToContacts( contact3 )
				
				if( !mailingList.save() ) {
					log.error "SAVING OF MAILING LIST FAILED:\n ${mailingList.errors}"
					return
				}
				
				if( !MailProperty.find( 'from MailProperty as mp where 1 = 1' ) ) {
					MailProperty prop = new MailProperty()
					
					prop.hostName = 'mail'
					prop.port = 25
					prop.tls = true
					prop.username = 'username'
					prop.password = 'password'
					prop.fromAddress = 'me@home.de'
					prop.fromName = 'Me'
					prop.subject = 'Test subject'
					prop.htmlMsg = '<html><body></body></html>'
					prop.textMsg = 'Text'
					prop.htmlSignature = 'MfG'
					prop.textSignature = 'MfG'
					prop.backupAddresses = 'name@company.com'
					prop.testAddresses = 'name@company.com'
					
					if( !prop.save() ) {
						log.error "SAVING OF MAILPROPERTY FAILED:\n ${prop.errors}"
						return
					}
				}
				
				log.info "Lineapp Domain Objects created!"
			
				break
			
			case "production": break
		}

	}
	
	def destroy = { }
}
