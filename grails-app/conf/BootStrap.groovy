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
					email: 'karsten.roethig@googlemail.com',
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
					email: 'karsten.roethig@googlemail.com',
					role: 'user',
					status: UserStatus.ACTIVE )
				
				if( !user.save() ) {
					log.error "SAVING OF USER FAILED:\n ${user.errors}"
					return
				}
				
				// create a headline (in user-role)
				Headline headline = new Headline()
				headline.subject = 'Urlaub endet für tausende in Regen und Stau'
				headline.subHeadline = 'Urlaub endet für tausende in Regen und Stau'
				headline.body = 'Urlaub endet für tausende in Regen, Stau und noch mehr'
				headline.federalLand = FederalLand.SN
				headline.location = 'Leipzig'
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
				
				// create a scene for the headline above
				Scene scene = new Scene()
				scene.body = 'Wir haben die Bilder'
				scene.headline = headline
				
				if( !scene.save() ) {
					log.error "SAVING OF SCENE FAILED:\n ${scene.errors}"
					return
				}
				
				// create some contacts
				def contact1 = new Contact( name: 'Kontakt 1', email: 'me@home.com' )
				contact1.save()
				def contact2 = new Contact( name: 'Kontakt 2', email: 'karstenr@gmx.net' )
				contact2.save()
				
				// create a mailing list
				MailingList mailingList = new MailingList( name: 'Verteiler 1' )
				mailingList.addToContacts( contact1 )
				mailingList.addToContacts( contact2 )
				
				if( !mailingList.save() ) {
					log.error "SAVING OF MAILING LIST FAILED:\n ${mailingList.errors}"
					return
				}
				
				log.info "Lineapp Domain Objects created!"
			
				break
			
			case "production": break
		}

	}
	
	def destroy = { }
}
