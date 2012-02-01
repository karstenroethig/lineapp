package karstenroethig.lineapp

class Contact {

	String name
	String email
	
	static belongsTo = [
		mailingList: MailingList
	]
	
	static constraints = {
		name( blank: false, nullable: false, size: 3..100 )
		email( email: true )
		mailingList()
	}
	
	String toString() {
		return "${name} (${email})"
	}
}
