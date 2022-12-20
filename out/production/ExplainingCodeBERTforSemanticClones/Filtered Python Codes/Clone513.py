def search(self, st) :
	if self.value == st :
		return True
	for child in self.children :
		if child.search(st) :
			return True
	return False


def search(self, st) :
	if self.value == st :
		return True
	else :
		if not self.children :
			return False
		else :
			return any(child.search(st) for child in self.children)

