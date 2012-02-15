package karstenroethig.lineapp

class MailingListController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [mailingListInstanceList: MailingList.list(params), mailingListInstanceTotal: MailingList.count()]
    }

    def create = {
        def mailingListInstance = new MailingList()
        mailingListInstance.properties = params
        return [mailingListInstance: mailingListInstance]
    }

    def save = {
        def mailingListInstance = new MailingList(params)
        if (mailingListInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'mailingList.label', default: 'MailingList'), mailingListInstance.id])}"
            redirect(action: "show", id: mailingListInstance.id)
        }
        else {
            render(view: "create", model: [mailingListInstance: mailingListInstance])
        }
    }

    def show = {
        def mailingListInstance = MailingList.get(params.id)
        if (!mailingListInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mailingList.label', default: 'MailingList'), params.id])}"
            redirect(action: "list")
        }
        else {
            [mailingListInstance: mailingListInstance]
        }
    }

    def edit = {
        def mailingListInstance = MailingList.get(params.id)
        if (!mailingListInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mailingList.label', default: 'MailingList'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [mailingListInstance: mailingListInstance]
        }
    }

    def update = {
        def mailingListInstance = MailingList.get(params.id)
        if (mailingListInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (mailingListInstance.version > version) {
                    
                    mailingListInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'mailingList.label', default: 'MailingList')] as Object[], "Another user has updated this MailingList while you were editing")
                    render(view: "edit", model: [mailingListInstance: mailingListInstance])
                    return
                }
            }
            mailingListInstance.properties = params
            if (!mailingListInstance.hasErrors() && mailingListInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'mailingList.label', default: 'MailingList'), mailingListInstance.id])}"
                redirect(action: "show", id: mailingListInstance.id)
            }
            else {
                render(view: "edit", model: [mailingListInstance: mailingListInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mailingList.label', default: 'MailingList'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def mailingListInstance = MailingList.get(params.id)
        if (mailingListInstance) {
            try {
                mailingListInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'mailingList.label', default: 'MailingList'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'mailingList.label', default: 'MailingList'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mailingList.label', default: 'MailingList'), params.id])}"
            redirect(action: "list")
        }
    }
}
