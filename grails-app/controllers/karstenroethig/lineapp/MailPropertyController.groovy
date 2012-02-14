package karstenroethig.lineapp

class MailPropertyController {

    static allowedMethods = [update: "POST"]

    def index = {
        redirect(action: "show")
    }

    def show = {
		def mailPropertyInstance = MailProperty.find( 'from MailProperty as mp where 1 = 1' )
        if (!mailPropertyInstance) {
            flash.message = "${message(code: 'mailProperty.not.found.message', args: [message(code: 'mailProperty.label', default: 'MailProperty')])}"
            redirect(controller: "headline",action: "list")
        }
        else {
            [mailPropertyInstance: mailPropertyInstance]
        }
    }

    def edit = {
        def mailPropertyInstance = MailProperty.get(params.id)
        if (!mailPropertyInstance) {
            flash.message = "${message(code: 'mailProperty.not.found.message', args: [message(code: 'mailProperty.label', default: 'MailProperty')])}"
            redirect(action: "show")
        }
        else {
            return [mailPropertyInstance: mailPropertyInstance]
        }
    }

    def update = {
        def mailPropertyInstance = MailProperty.get(params.id)
        if (mailPropertyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (mailPropertyInstance.version > version) {
                    
                    mailPropertyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'mailProperty.label', default: 'MailProperty')] as Object[], "Another user has updated this MailProperty while you were editing")
                    render(view: "edit", model: [mailPropertyInstance: mailPropertyInstance])
                    return
                }
            }
            mailPropertyInstance.properties = params
            if (!mailPropertyInstance.hasErrors() && mailPropertyInstance.save(flush: true)) {
                flash.message = "${message(code: 'mailProperty.updated.message', args: [message(code: 'mailProperty.label', default: 'MailProperty')])}"
                redirect(action: "show")
            }
            else {
                render(view: "edit", model: [mailPropertyInstance: mailPropertyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'mailProperty.not.found.message', args: [message(code: 'mailProperty.label', default: 'MailProperty')])}"
            redirect(action: "show")
        }
    }
}
