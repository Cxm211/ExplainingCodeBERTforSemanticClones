def runthis(* stringinput) :
	for t in stringinput :
		t = t.upper()
		print (t)
	print ()


def runthis(stringinput) :
	if isinstance(stringinput, list) :
		for t in stringinput :
			t = t.upper()
	elif isinstance(stringinput, basestring) :
		t = t.upper()
	else :
		raise Exception("Unknown type.")

