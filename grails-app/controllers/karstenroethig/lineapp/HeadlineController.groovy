package karstenroethig.lineapp

import org.apache.commons.io.FileUtils

class HeadlineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def mailService = new MailService()

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
			PropertyNumber prop = PropertyNumber.findByPropertyKey( 'headline.offerNumber.next' )
			headlineInstance.offerNumber = prop.value
			prop.value = prop.value + 1
			prop.save()
		}
		headlineInstance.author = session.user
		
        if (headlineInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'headline.label', default: 'Headline'), headlineInstance.offerNumber])}"
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
		if( !params.offerNumber?.trim()) {
			redirect(action: "list")
		} else {
			try{
				def searchOfferNumber = new Long( params.offerNumber.trim() )
				Headline headlineInstance = Headline.findByOfferNumber( searchOfferNumber )
				
				if( headlineInstance ) {
					render( view: "show", model: [headlineInstance: headlineInstance] )
                    return
				} else {
					flash.message = "${message(code: 'headline.search.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.offerNumber])}"
					redirect(action: "list")
				}
			}catch( Exception ex ) {
				flash.message = "${message(code: 'headline.search.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.offerNumber])}"
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
			
        	String attachmentDirectory = grailsApplication.config.lineapp.attachments.directory
			String errorResult = mailService.sendMail( headlineInstance, null, attachmentDirectory, true )
			
			if( errorResult ) {
				flash.message = "${message(code: 'headline.publish.testMail.error', args: [errorResult])}"
			}
			else {
				flash.message = "${message(code: 'headline.publish.testMail.success')}"
			}
			
			render(view: "publish", model: [headlineInstance: headlineInstance])
        }
	}
	
	def send = {
		def headlineInstance = Headline.get(params.id)
        if (!headlineInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.id])}"
            redirect(action: "list")
        }
        else {
			
			String errorResult
			
			if( params.mailingLists ) {
				MailingList[] mailingLists = MailingList.findAll( "from MailingList as ml where ml.name in (:names)", [names: params.mailingLists ] )
				String attachmentDirectory = grailsApplication.config.lineapp.attachments.directory
				
				errorResult = mailService.sendMail( headlineInstance, mailingLists, attachmentDirectory, false )
			}
			
			if( errorResult ) {
				flash.message = "${message(code: 'headline.publish.error', args: [message(code: 'headline.label', default: 'Headline'), headlineInstance.offerNumber, errorResult])}"
			}
			else {
				headlineInstance.status = HeadlineStatus.PUBLISHED
				headlineInstance.updateAuthor = session.user
				
				if (!headlineInstance.hasErrors() && headlineInstance.save(flush: true)) {
					flash.message = "${message(code: 'headline.publish.success', args: [message(code: 'headline.label', default: 'Headline'), headlineInstance.offerNumber])}"
					redirect(action: "show", id: headlineInstance.id)
				}
			}
			
			render(view: "publish", model: [headlineInstance: headlineInstance])
        }
	}
}
