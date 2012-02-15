package karstenroethig.lineapp

class SceneController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect( controller: "headline", action: "list", params: params)
    }

    def create = {
        def sceneInstance = new Scene()
        sceneInstance.properties = params
        return [sceneInstance: sceneInstance]
    }

    def save = {
        def sceneInstance = new Scene(params)
        if (sceneInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'scene.label', default: 'Scene'), sceneInstance.id])}"
            redirect( controller: "headline", action: "show", id: sceneInstance.headline.id )
        }
        else {
            render(view: "create", model: [sceneInstance: sceneInstance])
        }
    }

    def edit = {
        def sceneInstance = Scene.get(params.id)
        if (!sceneInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scene.label', default: 'Scene'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [sceneInstance: sceneInstance]
        }
    }

    def update = {
        def sceneInstance = Scene.get(params.id)
        if (sceneInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sceneInstance.version > version) {
                    
                    sceneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'scene.label', default: 'Scene')] as Object[], "Another user has updated this Scene while you were editing")
                    render(view: "edit", model: [sceneInstance: sceneInstance])
                    return
                }
            }
            sceneInstance.properties = params
            if (!sceneInstance.hasErrors() && sceneInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'scene.label', default: 'Scene'), sceneInstance.id])}"
                redirect( controller: "headline", action: "show", id: sceneInstance.headline.id )
            }
            else {
                render(view: "edit", model: [sceneInstance: sceneInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scene.label', default: 'Scene'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def sceneInstance = Scene.get(params.id)
        if (sceneInstance) {
            try {
				def headlineInstance = sceneInstance.headline
                sceneInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'scene.label', default: 'Scene'), params.id])}"
                redirect( controller: "headline", action: "show", id: headlineInstance.id )
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'scene.label', default: 'Scene'), params.id])}"
                redirect( action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scene.label', default: 'Scene'), params.id])}"
            redirect(action: "list")
        }
    }
}
