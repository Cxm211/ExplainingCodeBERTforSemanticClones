def __call__(self) :
	while True :
		next_action = self.queue.get()
		success = next_action(* self.args, ** self.kwargs)
		if not success :
			self.add_task(next_action)


def __call__(self, n) :
	if n not in self.cache :
		if n == 0 :
			self.cache [n] = 1
		else :
			self.cache [n] = n * self.__call__(n - 1)
	return self.cache [n]

