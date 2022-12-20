def check_all_conditions() :
	x = check_size()
	if x : return x
	x = check_color()
	if x : return x
	x = check_tone()
	if x : return x
	x = check_flavor()
	if x : return x
	return None


def check_all_conditions() :
	conditions = (check_size, check_color, check_tone, check_flavor)
	results_gen = dropwhile(lambda x : not x, imap(lambda check : check(), conditions))
	try :
		return results_gen.next()
	except StopIteration :
		return None

