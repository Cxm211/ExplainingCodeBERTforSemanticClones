def processData(data) :
	mutex.acquire()
	try :
		print ('Do some stuff')
	finally :
		mutex.release()


def processData(data, thread_safe) :
	if thread_safe :
		mutex.acquire()
	try :
		thread_id = threading.get_ident()
		print ('\nProcessing data:', data, "ThreadId:", thread_id)
	finally :
		if thread_safe :
			mutex.release()

