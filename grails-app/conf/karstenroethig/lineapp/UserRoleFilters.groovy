package karstenroethig.lineapp

class UserRoleFilters {

    def filters = {
	
		filterUser( controller: 'user', action: "(edit|show|update)" ) {
			before = {
				if( !session.user ) {
					redirect( controller: 'user', action: 'login' )
					return false
				}
			}
		}
	
		filterUserAdmin( controller: 'user', action: "(create|list)" ) {
			before = {
				if( !session.user ) {
					redirect( controller: 'user', action: 'login' )
					return false
				}
				
				if( !session.user.admin ) {
					redirect( controller: 'user', action: 'unauthorized' )
					return false
				}
			}
		}
	
		filterMailingList( controller: "(mailingList|contact|mailProperty)", action: '*' ) {
			before = {
				if( !session.user ) {
					redirect( controller: 'user', action: 'login' )
					return false
				}
				
				if( !session.user.admin ) {
					redirect( controller: 'user', action: 'unauthorized' )
					return false
				}
			}
		}
	
		filterHeadline( controller: "(headline|scene|attachment)", action: '*' ) {
			before = {
				if( !session.user ) {
					redirect( controller: 'user', action: 'login' )
					return false
				}
			}
		}
    }
    
}
