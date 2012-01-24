package karstenroethig.lineapp

class Headline {

	String subject
	String body
	FederalLand federalLand
	String location
	Date recordingDate
	String comment
	HeadlineStatus status = HeadlineStatus.UNPUBLISHED
	Long key
	Date dateCreated
	Date lastUpdated
	
	User author
	User updateAuthor
	
	static hasMany = [
		scenes: Scene
	]
	
	static constraints = {
		subject( blank: false, nullable: false, size: 3..255 )
		body( blank: false, nullable: false, size: 3..30000 )
		federalLand()
		location( blank: false, nullable: false, size: 3..255 )
		recordingDate( blank: false, nullable: false )
		comment( maxSize: 1000 )
		status( nullable: false )
		key( blank: false, nullable: false, unique: true, min: 1L )
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