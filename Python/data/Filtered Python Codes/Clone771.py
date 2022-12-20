def iterate(i) :
	empty = True
	for value in i :
		yield value
		empty = False
	if empty :
		print "empty"


def iterate(i) :
	try :
		i_iter = iter(i)
		next = i_iter.next()
	except StopIteration :
		print 'i is empty'
		return
	while True :
		yield next
		next = i_iter.next()

