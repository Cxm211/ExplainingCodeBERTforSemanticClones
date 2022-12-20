def __call__(self) :
	while True :
		next_action = self.queue.get()
		success = next_action(* self.args, ** self.kwargs)
		if not success :
			self.add_task(next_action)


def __call__(self, f) :
	def new_f() :
		print ("Entering", f.__name__)
		print ("p1=", self.p1)
		f()
		print ("Leaving", f.__name__)
	return new_f

