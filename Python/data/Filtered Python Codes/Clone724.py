def to_bool(bool_str) :
	if isinstance(bool_str, basestring) and bool_str :
		if bool_str.lower() in ['true', 't', '1'] : return True
		elif bool_str.lower() in ['false', 'f', '0'] : return False
	raise ValueError("%s is no recognized as a boolean value" % bool_str)


def to_bool(value) :
	if type(value) == type('') :
		if value.lower() in ("yes", "y", "true", "t", "1") :
			return True
		if value.lower() in ("no", "n", "false", "f", "0", "") :
			return False
		raise Exception('Invalid value for boolean conversion: ' + value)
	return bool(value)

