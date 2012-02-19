package karstenroethig.lineapp

class PropertyNumber {

	String propertyKey
	Long value
	
    static constraints = {
		propertyKey( blank: false, nullable: false, unique: true, size: 3..255 )
		value( blank: false, nullable: false, min: 1L )
    }
}
