def remove_user(self, user) :
	if hasattr(user, "name") :
		self.remove(user.name)
	else :
		self.remove(user)


def remove_user(self, user_or_username) :
	try :
		remote.remove(user_or_username.name)
	except AttributeError :
		remote.remove(user_or_username)

