package karstenroethig.lineapp

class MailingList {

	String name
	
	static hasMany = [
		contacts: Contact
	]
	
	static constraints = {
		name ( nullable: false, blank: false, size: 3..100, unique: true )
		contacts()
	}
	
	String toString() {
		return "${name}"
	}
}
