package karstenroethig.lineapp

class Headline {

	String subject
	String subHeadline
	String body
	FederalLand federalLand
	String location
	Date recordingDate
	String comment
	HeadlineStatus status = HeadlineStatus.UNPUBLISHED
	Long offerNumber
	Date dateCreated
	Date lastUpdated
	
	User author
	User updateAuthor
	
	static hasMany = [
		scenes: Scene,
		attachments: Attachment
	]
	
	static constraints = {
		subject( blank: false, nullable: false, size: 3..10000 )
		subHeadline( maxSize: 10000 )
		body( blank: false, nullable: false, size: 3..300000 )
		federalLand()
		location( blank: false, nullable: false, size: 3..255 )
		recordingDate( blank: false, nullable: false )
		comment( maxSize: 10000 )
		status( nullable: false )
		offerNumber( blank: false, nullable: false, unique: true, min: 1L )
		author( nullable: false )
		dateCreated()
		updateAuthor( nullable: true )
		lastUpdated()
		scenes()
	}
}

enum FederalLand {
	EMPTY( '' ),
	BW( 'Baden-Württemberg' ),
	BY( 'Bayern' ),
	BE( 'Berlin' ),
	BB( 'Brandenburg' ),
	HB( 'Bremen' ),
	HH( 'Hamburg' ),
	HE( 'Hessen' ),
	MV( 'Mecklenburg-Vorpommern' ),
	NI( 'Niedersachsen' ),
	NW( 'Nordrhein-Westfalen' ),
	RP( 'Rheinland-Pfalz' ),
	SL( 'Saarland' ),
	SN( 'Sachsen' ),
	ST( 'Sachsen-Anhalt' ),
	SH( 'Schleswig-Holstein' ),
	TH( 'Thüringen' )
	
	String fullname
	
	FederalLand( String fullname ) {
		this.fullname = fullname
	}
	
	String toString() {
		return fullname
	}
}

enum HeadlineStatus {
	UNPUBLISHED( 'Unveröffentlicht' ),
	PUBLISHED( 'Veröffentlicht' )
	
	String name
	
	HeadlineStatus( String name ) {
		this.name = name
	}
	
	String toString() {
		return name
	}
}