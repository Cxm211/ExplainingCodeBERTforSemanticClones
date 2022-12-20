def __setattr__(self, name, value) :
	if not hasattr(self, name) :
		raise AttributeError("Model instances do not accept arbitrary attributes")
	else :
		object.__setattr__(self, name, value)


def __setattr__(self, attribute, value) :
	if not attribute in self.__class__.__allowed_attr :
		raise AttributeError
	else :
		super().__setattr__(attribute, value)

