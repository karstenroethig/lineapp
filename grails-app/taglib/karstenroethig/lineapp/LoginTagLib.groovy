package karstenroethig.lineapp

class LoginTagLib {

	static namespace = "myLogin"

	def loginControl = {
		
		if( request.getSession( false ) && session.user ) {
			// out << "${session.user.fullname} "
			out << """${link( controller: "user", action: "profile", elementId: "header-profile-link" ){ "${session.user.fullname}" }}"""
			out << " "
			out << """[${link( controller: "user", action: "logout" ){ "Abmelden" }}]"""
		} else {
			out << """[${link( controller: "user", action: "login" ){ "Anmelden" }}]"""
		}
	}
}
