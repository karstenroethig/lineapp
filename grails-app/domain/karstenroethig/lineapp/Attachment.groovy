package karstenroethig.lineapp

import org.apache.commons.io.FileUtils

class Attachment {

	String mimetype
	String filename
	Long filesize
	User author
	Date dateCreated
	
	static belongsTo = [
		headline: Headline
	]

    static constraints = {
    	mimetype( blank: false, nullable: false, maxSize: 255 )
    	filename( blank: false, nullable: false, maxSize: 255 )
    	filesize( blank: false, nullable: false, min: 1L )
    	author( nullable: false )
    }
    
    static transients = ['formattedFilesize']
    
    String getFormattedFilesize() {
    	return FileUtils.byteCountToDisplaySize( filesize )
    }
}
