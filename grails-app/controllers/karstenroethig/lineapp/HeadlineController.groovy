package karstenroethig.lineapp

import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils

class HeadlineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def mailService = new MailService()

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [
			headlineInstanceList: Headline.list(params),
			headlineInstanceTotal: Headline.count(),
			search_param_text_opt_headline:true,
			search_param_text_opt_subHeadline:true,
			search_param_text_opt_body:true
		]
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
	
	def quicksearch = {
		if( !params.quicksearchString?.trim()) {
			redirect(action: "list")
		} else {
			try{
				def searchOfferNumber = new Long( params.quicksearchString.trim() )
				Headline headlineInstance = Headline.findByOfferNumber( searchOfferNumber )
				
				if( headlineInstance ) {
					render( view: "show", model: [headlineInstance: headlineInstance] )
                    return
				} else {
					flash.message = "${message(code: 'headline.search.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.quicksearchString])}"
					redirect( action: "search", params:[ search_param_text:params.quicksearchString, search_param_text_opt_headline:true, search_param_text_opt_subHeadline:true, search_param_text_opt_body:true ])
				}
			}catch( Exception ex ) {
				flash.message = "${message(code: 'headline.search.not.found.message', args: [message(code: 'headline.label', default: 'Headline'), params.quicksearchString])}"
				redirect( action: "search", params:[ search_param_text:params.quicksearchString, search_param_text_opt_headline:true, search_param_text_opt_subHeadline:true, search_param_text_opt_body:true ])
			}
		}
	}
	
	def search = {
	
		def search_param_text = params.search_param_text
		def search_param_text_opt_headline = params.search_param_text_opt_headline
		def search_param_text_opt_subHeadline = params.search_param_text_opt_subHeadline
		def search_param_text_opt_body = params.search_param_text_opt_body
		def search_param_text_opt_comment = params.search_param_text_opt_comment
		def search_param_text_opt_scenes = params.search_param_text_opt_scenes
		def search_param_status_str = params.search_param_status
		def search_param_status = null
		
		for( HeadlineStatus headlineStatus : HeadlineStatus.values() ) {
			if( StringUtils.equals( search_param_status_str, headlineStatus.name() ) ) {
				search_param_status = headlineStatus
			}
		}

		if( StringUtils.isBlank( search_param_text )
			&& search_param_status == null ) {
			
			flash.message = "${message(code: 'headline.search.noParams', default: 'No search parameter entered')}"
			
			render( view: "list", model: [
				headlineInstanceList: [],
				headlineInstanceTotal: 0,
				search_param_text_opt_headline: true,
				search_param_text_opt_subHeadline: true,
				search_param_text_opt_body: true
			] )
			
			return
		}
		
		def criteria = Headline.createCriteria()
		def results = criteria.list {
			
			and {
				if( StringUtils.isNotBlank( search_param_text ) ) {
				
					or {
						if( search_param_text_opt_headline ) {
							ilike( "subject", "%" + search_param_text + "%" )
						}
					
						if( search_param_text_opt_subHeadline ) {
							ilike( "subHeadline", "%" + search_param_text + "%" )
						}
					
						if( search_param_text_opt_body ) {
							ilike( "body", "%" + search_param_text + "%" )
						}
					
						if( search_param_text_opt_comment ) {
							ilike( "comment", "%" + search_param_text + "%" )
						}
						
						if( search_param_text_opt_scenes ) {
							scenes {
								ilike( "body", "%" + search_param_text + "%" )
							}
						}
					}
				}
				
				if( search_param_status != null ) {
					eq( "status", search_param_status )
				}
			}
		}

		if( results.size == 0 ) {
			flash.message = "${message(code: 'headline.search.noResults', default: 'No search results found')}"
		
			render( view: "list", model: [
				headlineInstanceList: [],
				headlineInstanceTotal: 0,
				search_param_text: search_param_text,
				search_param_text_opt_headline: search_param_text_opt_headline,
				search_param_text_opt_subHeadline: search_param_text_opt_subHeadline,
				search_param_text_opt_body: search_param_text_opt_body,
				search_param_text_opt_comment: search_param_text_opt_comment,
				search_param_text_opt_scenes: search_param_text_opt_scenes,
				search_param_status: search_param_status_str
			] )
			
			return
		}
		
		render( view: "list", model: [
			headlineInstanceList: results,
			headlineInstanceTotal: results.size,
			search_param_text: search_param_text,
			search_param_text_opt_headline: search_param_text_opt_headline,
			search_param_text_opt_subHeadline: search_param_text_opt_subHeadline,
			search_param_text_opt_body: search_param_text_opt_body,
			search_param_text_opt_comment: search_param_text_opt_comment,
			search_param_text_opt_scenes: search_param_text_opt_scenes,
			search_param_status: search_param_status_str
		] )
	}
	
	def resetSearch = {
		redirect( action: "list" )
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
