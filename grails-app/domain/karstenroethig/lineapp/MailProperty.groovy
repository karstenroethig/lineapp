package karstenroethig.lineapp

class MailProperty {

	String hostName
	Integer port
	Boolean tls
	String username
	String password
	String fromAddress
	String fromName
	String subject
	String htmlMsg
	String textMsg
	String htmlSignature
	String textSignature
	String backupAddresses
	String testAddresses

	static constraints = {
		hostName( blank: false, nullable: false, maxSize: 255 )
		port( min: 0, max: 65535 )
		tls( blank: false, nullable: false )
		username( blank: false, nullable: false, maxSize: 255 )
		password( blank: false, nullable: false, maxSize: 255 )
		fromAddress( blank: false, nullable: false, email: true )
		fromName( blank: false, nullable: false, maxSize: 255 )
		subject( blank: false, nullable: false, size: 3..255 )
		htmlMsg( blank: false, nullable: false, maxSize: 10000 )
		textMsg( blank: false, nullable: false, maxSize: 10000 )
		htmlSignature( maxSize: 1000 )
		textSignature( maxSize: 1000 )
		backupAddresses( maxSize: 1000 )
		testAddresses( maxSize: 1000 )
	}
}
