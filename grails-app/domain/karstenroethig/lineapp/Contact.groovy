package karstenroethig.lineapp

class Contact {

	String name
	String email
	String fax
	
	static belongsTo = [
		mailingList: MailingList
	]
	
	static constraints = {
		name( blank: false, nullable: false, size: 3..100 )
		email( email: true )
		fax( size: 0..20 , matches: "[0-9]+")
		mailingList()
	}
	
	String toString() {
		return "${name} (${email}, ${fax})"
	}
}
