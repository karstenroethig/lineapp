package karstenroethig.lineapp

class PropertyNumber {

	String key
	Long value
	
    static constraints = {
		key( blank: false, nullable: false, unique: true, size: 3..255 )
		value( blank: false, nullable: false, min: 1L )
    }
}
