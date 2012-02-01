package karstenroethig.lineapp

import org.apache.commons.io.FileUtils

class HeadlineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [headlineInstanceList: Headline.list(params), headlineInstanceTotal: Headline.count()]
    }

    def create = {
        def headlineInstance = new Headline()
        headlineInstance.properties = params
        return [headlineInstance: headlineInstance]
    }

    def save = {
        def headlineInstance = new Headline(params)
		
		synchronized( this ) {
			PropertyNumber prop = PropertyNumber.findByKey( 'headline.key.next' )
			headlineInstance.key = prop.value
			prop.value = prop.value + 1
			prop.save()
		}
		headlineInstance.author = session.user
		
        if (headlineInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'headline.label', default: 'Headline'), headlineInstance.key])}"
            redirect(action: "show", id: headlineInstance.id)
        }
        else {
            render(view: "create", model: [headlineInstance: headlineInstance])
        }
    }

    def show = {
        def headlineInstance = Headline.get(params.id)
        if (!headlineInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
        else {
            [headlineInstance: headlineInstance]
        }
    }

    def edit = {
        def headlineInstance = Headline.get(params.id)
        if (!headlineInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [headlineInstance: headlineInstance]
        }
    }

    def update = {
        def headlineInstance = Headline.get(params.id)
        if (headlineInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (headlineInstance.version > version) {
                    
                    headlineInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'headline.label', default: 'Headline')] as Object[], "Another user has updated this Headline while you were editing")
                    render(view: "edit", model: [headlineInstance: headlineInstance])
                    return
                }
            }
            headlineInstance.properties = params
			
			headlineInstance.updateAuthor = session.user
			
            if (!headlineInstance.hasErrors() && headlineInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'headline.label', default: 'Headline'), headlineInstance.id])}"
                redirect(action: "show", id: headlineInstance.id)
            }
            else {
                render(view: "edit", model: [headlineInstance: headlineInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def headlineInstance = Headline.get(params.id)
        if (headlineInstance) {
            try {
                headlineInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def search = {
		if( !params.key?.trim()) {
			redirect(action: "list")
		} else {
			try{
				def searchKey = new Long( params.key.trim() )
				Headline headlineInstance = Headline.findByKey( searchKey )
				
				if( headlineInstance ) {
					render( view: "show", model: [headlineInstance: headlineInstance] )
                    return
				} else {
					flash.message = "${message(code: 'headline.search.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.key])}"
					redirect(action: "list")
				}
			}catch( Exception ex ) {
				flash.message = "${message(code: 'headline.search.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.key])}"
				redirect(action: "list")
			}
		}
	}
	
	def publish = {
		def headlineInstance = Headline.get(params.id)
        if (!headlineInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [headlineInstance: headlineInstance]
        }
	}
	
	def testMail = {
		def headlineInstance = Headline.get(params.id)
        if (!headlineInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
        else {
			def jasperParams = [:]
			
			jasperParams.subject = headlineInstance.subject
			jasperParams.body = headlineInstance.body
			
			boolean first = true
			jasperParams.scenes = ""
			
			for( scene in headlineInstance.scenes ) {
				
				if( !first ) {
					jasperParams.scenes += "\n"
				} else {
					first = false
				}
				
				jasperParams.scenes += "- "
				jasperParams.scenes += scene.body
			}
			
			jasperParams.dateLocation = headlineInstance.federalLand.fullname + " / " + headlineInstance.location + " - " + headlineInstance.recordingDate.format( 'dd.MM.yyyy' ) 
			jasperParams.contact = "Name, Vorname\nAdresszeile 1\nAddresszeile 2\nTeläfon"
			
			jasperParams.formatSubject = 1
			
			println jasperParams
        }
		
		def filename = "HTC.pdf"
		def file = new File( "C:/develop/Grails" + File.separatorChar + filename )
		
		byte[] content = FileUtils.readFileToByteArray( file )
		
		response.setHeader( "Content-disposition", "attachment; filename=" + filename )
		// response.contentType = "application/octet-stream"
		response.outputStream << content
	}
	
	def send = {
		def headlineInstance = Headline.get(params.id)
        if (!headlineInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
        else {
			// TODO E-Mails senden
			// bei Fehler auf Seite bleiben und Meldung anzeigen
			// bei keinem Fehler: Status setzen, auf show und Erfolgsmeldunganzeigen
			
			headlineInstance.status = HeadlineStatus.PUBLISHED
			headlineInstance.updateAuthor = session.user
			
			if (!headlineInstance.hasErrors() && headlineInstance.save(flush: true)) {
				flash.message = "${message(code: 'headline.publish.success', args: [message(code: 'headline.label', default: 'Headline'), headlineInstance.key])}"
				redirect(action: "show", id: headlineInstance.id)
			}
			else {
				render(view: "publish", model: [headlineInstance: headlineInstance])
			}
        }
	}
}
