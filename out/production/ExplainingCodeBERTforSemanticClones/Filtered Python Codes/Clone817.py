def get_value(d, ks) :
	for k in ks :
		try :
			d = d [k]
		except (KeyError, TypeError) :
			return 0
	return d


def get_value(mydict, keys) :
	if not keys :
		return mydict
	key = keys [0]
	try :
		newdict = mydict [key]
	except (TypeError, KeyError) :
		return 0
	return get_value(newdict, keys [1 :])

