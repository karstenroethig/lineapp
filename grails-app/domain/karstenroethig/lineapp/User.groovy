package karstenroethig.lineapp

class User {

	String username
	String fullname
	String email
	String passwordHash
	UserStatus status = UserStatus.ACTIVE
	String role
	
	static constraints = {
		username( blank: false, nullable: false, size: 3..30, unique: true, matches: "[a-zA-Z0-9]+" )
		fullname( blank: false, nullable: false, size: 3..100 )
		email( email: true )
		passwordHash( password: true, blank: false, nullable: false, size: 3..255 )
		status( nullable: false )
		role( inList: [ 'admin', 'user' ] )
	}
	
	static transients = [ 'admin' ]
	
	boolean isAdmin() {
		return role == "admin"
	}
	
	String toString() {
		return "${fullname} (${username})"
	}
}

enum UserStatus {
	ACTIVE( 'Aktiv' ),
	INACTIVE( 'Inaktiv' )
	
	String name
	
	UserStatus( String name ) {
		this.name = name
	}
	
	String toString() {
		return name
	}
}