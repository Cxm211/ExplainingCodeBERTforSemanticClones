def __init__(self, key, value = None) :
	self.key = key
	self.value = value
	if not key in Master.existent :
		Master.existent [key] = self


def __init__(self, key, value = None) :
	if not Master.init_OK :
		raise Exception('Direct call to Master() is not allowed')
	Master.init_OK = False
	self.key = key
	self.value = value
	Master.existent [key] = self

