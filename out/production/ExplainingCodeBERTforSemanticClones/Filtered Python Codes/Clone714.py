def increment(self) :
	for i, num in enumerate(reversed(self.numbers)) :
		if num :
			self.numbers [- i - 1] = 0 if num == 9 else num + 1
			break


def increment(self) :
	i = len(self.number) - 1
	while i > = 0 :
		while (self.number [i] < 9) :
			self.number [i] += 1
		self.number [i] = 0
		i -= 1

