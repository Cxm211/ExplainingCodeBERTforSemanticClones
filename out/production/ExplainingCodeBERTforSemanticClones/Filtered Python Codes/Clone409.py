def ignore_until(the_list, match) :
	if match in the_list :
		index = the_list.index(match)
		return the_list [index :]
	else :
		return []


def ignore_until(yourlist, match) :
	try :
		return yourlist [yourlist.index(match) :]
	except ValueError :
		return []

