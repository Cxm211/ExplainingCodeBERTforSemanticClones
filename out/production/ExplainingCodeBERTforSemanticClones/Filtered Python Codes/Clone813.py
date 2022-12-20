def get_with_default(colour, L, default = '') :
	temp = None
	for d in L :
		if d ['color'] == colour :
			return d
		elif d ['color'] == default :
			temp = d
	return temp


def get_with_default(colour, colours, default) :
	search = (d for d in colours if d ['color'] in (colour, default))
	match_or_default = next(search)
	if match_or_default ['color'] ! = default or default == colour :
		return match_or_default
	return next(search, match_or_default)

