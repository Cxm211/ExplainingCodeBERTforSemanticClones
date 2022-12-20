def convertType(value) :
	try :
		return int(value) if value.strip().isdigit() else float(value)
	except :
		return value


def convertType(value) :
	try :
		return float(value)
	except : pass
	try :
		return int(value)
	except :
		return value

