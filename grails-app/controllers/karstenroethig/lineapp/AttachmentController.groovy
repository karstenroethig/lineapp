package karstenroethig.lineapp

import java.io.File
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

class AttachmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "GET"]

    def index = {
        redirect( controller: "headline", action: "list", params: params )
    }

    def create = {
        def attachmentInstance = new Attachment()
        attachmentInstance.properties = params
        return [attachmentInstance: attachmentInstance]
    }

    def save = {
    	
    	def attachmentInstance = new Attachment(params)
    	def file = request.getFile('file')
    	
    	if( file.isEmpty() ) {
    		flash.message = "${message(code: 'attachment.file.empty')}"
    		render(view: "create", model: [attachmentInstance: attachmentInstance])
    	} else {
    		attachmentInstance.filename = FilenameUtils.getName( file.getOriginalFilename() )
    		attachmentInstance.mimetype = file.getContentType()
    		attachmentInstance.filesize = file.getSize()
    		attachmentInstance.author = session.user
    		
			if (attachmentInstance.save(flush: true)) {
				
				try {
					String path = grailsApplication.config.lineapp.attachments.directory
					path += "headline_" + attachmentInstance.headline.id
					
					File dir = new File( path )
					
					if( !dir.exists() ) {
						dir.mkdirs();
					}
					
					String filename = attachmentInstance.id + "_" + attachmentInstance.filename
					
					file.transferTo( new File( dir, filename ) )
					
					flash.message = "${message(code: 'default.created.message', args: [message(code: 'attachment.label', default: 'Attachment'), attachmentInstance.filename])}"
					redirect( controller: "headline", action: "show", id: attachmentInstance.headline.id )
	            
				}catch( Exception ex ) {
					flash.message = "${message(code: 'attachment.file.copyError')}"
					redirect( controller: "headline", action: "show", id: attachmentInstance.headline.id )
				}
	            
	        } else {
	        	render(view: "create", model: [attachmentInstance: attachmentInstance])
	        }
    	}
    }
    
    def show = {
    	def attachmentInstance = Attachment.get(params.id)
    	
    	if( attachmentInstance ) {
    		
    		String path = grailsApplication.config.lineapp.attachments.directory
			path += "headline_" + attachmentInstance.headline.id
			
			String filename = attachmentInstance.id + "_" + attachmentInstance.filename
			
			File file = new File( path, filename )
			
			if( !file.exists() ) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attachment.label', default: 'Attachment'), params.id])}"
			    redirect( controller: "headline", action: "list")
			}
			
			byte[] content = FileUtils.readFileToByteArray( file )
			
			response.contentType = attachmentInstance.mimetype
			response.addHeader( "Content-Length", "${attachmentInstance.filesize}")
			
			if( params.download ) {
				response.addHeader( "Content-disposition", "attachment; filename=\"${attachmentInstance.filename}\"" )
			}
			
			def os = response.outputStream
			
			os << content
			IOUtils.closeQuietly( os )
			
    	} else {
    		flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attachment.label', default: 'Attachment'), params.id])}"
    		redirect( controller: "headline", action: "list")
    	}
    }

    def delete = {
    	def attachmentInstance = Attachment.get(params.id)
    	    	
    	if( attachmentInstance ) {
    		
    		def headlineInstance = attachmentInstance.headline
    		
			String path = grailsApplication.config.lineapp.attachments.directory
			path += "headline_" + attachmentInstance.headline.id
			
			String filename = attachmentInstance.id + "_" + attachmentInstance.filename
			
			File file = new File( path, filename )
    		
    		try {
				attachmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'attachment.label', default: 'Attachment'), params.id])}"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'attachment.label', default: 'Attachment'), params.id])}"
            }
    		
    		FileUtils.deleteQuietly( file )
    		
    		redirect( controller: "headline", action: "show", id: headlineInstance.id )
			
    	} else {
    		flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'attachment.label', default: 'Attachment'), params.id])}"
    		redirect( controller: "headline", action: "list")
    	}
    }
}
