def run(self) :
	while True :
		image = self.tasks_q.get()
		time.sleep(1)
		self.results_q.put("text")


def run(self) :
	while True :
		frameNum, frameData = self.task_queue.get()
		m = random.randint(0, 1000000)
		while m > = 0 :
			m -= 1
		self.result_queue.put("result from image " + str(frameNum))
	return

