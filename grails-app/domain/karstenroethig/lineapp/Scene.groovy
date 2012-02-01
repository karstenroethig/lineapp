package karstenroethig.lineapp

class Scene {

	String body
	Integer sequence = 0
	
	static belongsTo = [
		headline: Headline
	]
	
	static constraints = {
		body( blank: false, nullable: false, size: 3..300000 )
		sequence()
		headline()
	}
	
	String toString() {
		return "${body}"
	}
}
