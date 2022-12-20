def checkLen() :
	days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
	for position, day in enumerate(days) :
		if day == "Monday" :
			print ("Found it")
			print (position)


def checkLen() :
	days = ["Monday", "Tuesday", "Wednesday", "Thursday" "Friday", "Saturday", "Sunday"]
	try :
		position = days.index("Monday")
		print ("Found it")
	except ValueError :
		position = None
		print ("Not present")
	print (position)

