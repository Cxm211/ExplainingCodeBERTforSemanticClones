def __init__(self) :
	self.fields = []
	for field_name in dir(self) :
		field = getattr(self, field_name)
		if isinstance(field, Field) :
			field.name = field_name
			self.fields.append(field)
	self.fields.sort(key = operator.attrgetter('count'))


def __init__(self, * args, ** kwargs) :
	super(ContestForm, self).__init__(* args, ** kwargs)
	self.fields.keyOrder = [
	'name',
	'description',
	'image',
	'video_link',
	'category']

