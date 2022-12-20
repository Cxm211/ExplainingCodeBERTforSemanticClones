def f() :
	for key, val in measurements.items() :
		exec ('global {};{} = {}'.format(key, key, val))
	print ('tg: ', tg)
	vars = globals()
	for key in measurements.keys() :
		print ('Key: ', key, ', Value: ', vars [key])


def f() :
	ldict = {}
	for key, val in measurements.items() :
		ldict.update(locals())
		exec (key + ' = val', globals(), ldict)
		key = ldict [key]
	print (ldict ['tg'])

