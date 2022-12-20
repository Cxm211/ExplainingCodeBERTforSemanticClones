def __call__(self) :
	while True :
		next_action = self.queue.get()
		success = next_action(* self.args, ** self.kwargs)
		if not success :
			self.add_task(next_action)


def __call__(self, file) :
	hash = self.algorithm()
	with open(file, 'rb') as f :
		for chunk in iter(lambda : f.read(4096), '') :
			hash.update(chunk)
	return hash.hexdigest()

