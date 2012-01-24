package karstenroethig.lineapp

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create = {
        def userInstance = new User()
        userInstance.properties = params
        return [userInstance: userInstance]
    }

    def save = {
        def userInstance = new User(params)
		userInstance.passwordHash = params.password.encodeAsSHA()
        if (userInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
            redirect(action: "show", id: userInstance.id)
        }
        else {
            render(view: "create", model: [userInstance: userInstance])
        }
    }

    def show = {
		
		if( !session.user.admin && session.user.id.toString() != params.id ) {
			redirect( action: "unauthorized" )
			return false
		}
	
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
    }

    def edit = {
	
		if( !session.user.admin && session.user.id.toString() != params.id ) {
			redirect( action: "unauthorized" )
			return false
		}
		
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userInstance: userInstance]
        }
    }

    def update = {
	
		if( !session.user.admin && session.user.id.toString() != params.id ) {
			redirect( action: "unauthorized" )
			return false
		}
	
        def userInstance = User.get(params.id)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
            userInstance.properties = params
			if( !params.password.equals( "" ) ) {
				userInstance.passwordHash = params.password.encodeAsSHA()
			}
            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
                redirect(action: "show", id: userInstance.id)
            }
            else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }

	def login = {}
	
	def logout = {
		flash.message = "Goodbye ${session.user.fullname}"
		session.user = null
		redirect( action: "login" )
	}
	
	def authenticate = {
		def user = User.findByUsernameAndPasswordHash( params.login, params.password.encodeAsSHA() )
		
		if( user && user.status == UserStatus.ACTIVE ) {
			session.user = user
			flash.message = "Hallo ${user.fullname}!"
			redirect( controller: "headline", action: "list" )
		} else {
			flash.message = "Sorry, ${params.login}. Please try again."
			redirect( action: "login" )
		}
	}
	
	def profile = {
		
		if( !session.user ) {
			redirect( action: "login" )
		} else {
			redirect( action: "show", id: session.user.id )
		}
	}
	
	def unauthorized = {
	
		if( !session.user ) {
			redirect( action: "login" )
		} else {
			render 'Du bist hierfür nicht berechtigt.'
		}
	}
}
